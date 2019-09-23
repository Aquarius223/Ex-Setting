package com.miui.purify.prefence;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class purifySeekBarPreference extends DialogPreference implements purifySeekBar.OnSeekBarHintProgressChangeListener {
    public purifyCoreSettings CoreSettings = new purifyCoreSettings();
    private int mDefaultValue;
    private String mHintStyle;
    private int mHintWidth;
    private int mMax;
    private int mMin;
    private purifySeekBar mMiuiSeekBar;
    private String[] mPrSfSummary;
    private int mProgress;
    private boolean mShow;
    private int mStep;
    private int mYOffset;

    public purifySeekBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.CoreSettings.initialization(context, attributeSet, getKey());
        this.mPrSfSummary = this.CoreSettings.initSummary(getSummary());
        this.mShow = attributeSet.getAttributeBooleanValue(null, "Show", false);
        initialization();
    }

    private void initialization() {
        setMin(this.CoreSettings.getAttributeInt("min", 0));
        setMax(this.CoreSettings.getAttributeInt("max", 100));
        setStep(this.CoreSettings.getAttributeInt("step", 1));
        if (this.mShow) {
            this.mHintWidth = this.CoreSettings.getAttributeInt("HintWidth", 100);
            this.mHintStyle = this.CoreSettings.getAttributeValue("HintStyle");
            this.mYOffset = this.CoreSettings.getAttributeInt("HintOffset", 15);
        }
    }

    private void setProgress(int i, boolean z) {
        int i2 = i > this.mMax ? this.mMax : i;
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 != this.mProgress) {
            this.mProgress = i2;
            persistInt(i2);
            if (z) {
                notifyChanged();
            }
        }
    }

    public boolean getDependents(Integer num) {
        return num.intValue() <= 0;
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        this.mMiuiSeekBar = (purifySeekBar) view.findViewById(this.CoreSettings.IDtoID("miui_seekbar"));
        if (this.mShow) {
            this.mMiuiSeekBar.setHintWidth(this.mHintWidth);
        } else {
            this.mMiuiSeekBar.setHintWidth(0);
        }
        this.mMiuiSeekBar.mYOffset = this.mYOffset;
        this.mMiuiSeekBar.setHintStyle(this.mHintStyle);
        this.mMiuiSeekBar.setHintView(view, this.mMiuiSeekBar);
        this.mMiuiSeekBar.setOnProgressChangeListener(this);
        this.mMiuiSeekBar.mMax = this.mMax;
        this.mMiuiSeekBar.mMin = this.mMin;
        this.mMiuiSeekBar.mStep = this.mStep;
        this.mMiuiSeekBar.mYOffset = this.mYOffset >= 10 ? this.mYOffset : 10;
        this.mMiuiSeekBar.mProgress = this.mProgress;
        this.mMiuiSeekBar.setMax((this.mMax - this.mMin) / this.mStep);
        this.mMiuiSeekBar.setProgress((this.mProgress - this.mMin) / this.mStep);
        this.mMiuiSeekBar.setEnabled(isEnabled());
    }

    /* access modifiers changed from: protected */
    public View onCreateView(ViewGroup viewGroup) {
        ViewGroup viewGroup2 = (ViewGroup) super.onCreateView(viewGroup);
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.CoreSettings.LayoutToID("seekbar_preference_layout"), null);
        ((ViewGroup) inflate).addView(viewGroup2, 0);
        return inflate;
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        int i2 = typedArray.getInt(i, 0);
        this.mDefaultValue = i2;
        return Integer.valueOf(i2);
    }

    public String onHintTextChanged(purifySeekBar miuiSeekBar, int i) {
        return null;
    }

    public void onMiuiSeekBarProgress(int i) {
        this.mProgress = (this.mStep * i) + this.mMin;
        this.mMiuiSeekBar.setProgress(this.mProgress);
        setSummary(String.valueOf(this.mProgress));
        notifyDependencyChange(getDependents(Integer.valueOf(this.mProgress)));
        this.CoreSettings.setIntegerValue(this.mProgress);
        this.CoreSettings.executeCore(String.valueOf(this.mProgress));
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable.getClass().equals(SavedState.class)) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.mProgress = savedState.progress;
            this.mMax = savedState.max;
            this.mStep = savedState.step;
            this.mMin = savedState.min;
            notifyChanged();
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        SavedState savedState = new SavedState(onSaveInstanceState);
        savedState.progress = this.mProgress;
        savedState.max = this.mMax;
        savedState.min = this.mMin;
        savedState.step = this.mStep;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(boolean z, Object obj) {
        int intValue = this.CoreSettings.getIntegerValue(obj != null ? this.mDefaultValue : 0).intValue();
        if (VERSION.SDK_INT > 23) {
            setProgress(intValue);
            setSummary(String.valueOf(intValue));
            if (intValue == this.mDefaultValue) {
                this.CoreSettings.executeCore(String.valueOf(mDefaultValue));
            }
        } else if (z) {
            setProgress(intValue);
            setSummary(String.valueOf(intValue));
        } else {
            this.CoreSettings.setIntegerValue(intValue);
            setProgress(intValue);
            setSummary(String.valueOf(intValue));
            this.CoreSettings.executeCore(String.valueOf(intValue));
        }
    }

    public void setMax(int i) {
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
    }

    public void setMin(int i) {
        if (i != this.mMin) {
            this.mMin = i;
            notifyChanged();
        }
    }

    public void setProgress(int i) {
        setProgress(i, true);
    }

    public void setStep(int i) {
        if (i != this.mStep) {
            this.mStep = i;
            notifyChanged();
        }
    }

    public void setSummary(CharSequence charSequence) {
        super.setSummary(this.mPrSfSummary[0] + charSequence + this.mPrSfSummary[1]);
    }

    public boolean shouldDisableDependents() {
        return this.CoreSettings.getIntegerValue(this.mProgress).intValue() <= 0;
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int max;
        int min;
        int progress;
        int step;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.max = parcel.readInt();
            this.min = parcel.readInt();
            this.step = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
            parcel.writeInt(this.min);
            parcel.writeInt(this.step);
        }
    }
}
