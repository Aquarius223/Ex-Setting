package com.miui.purify.prefence;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class purifyEditText extends EditTextPreference {
    public purifyCoreSettings CoreSettings = new purifyCoreSettings();
    private String mLastState;
    private String[] mPrSfSummary;

    public purifyEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.CoreSettings.initialization(context, attributeSet, getKey());
        this.mPrSfSummary = this.CoreSettings.initSummary(getSummary());
    }

    public boolean getDependents(String str) {
        try {
            return Integer.parseInt(str) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        String text = getText();
        if (this.mLastState != text) {
            notifyDependencyChange(getDependents(text));
            this.CoreSettings.setStringValue(text);
            this.CoreSettings.executeCore(text);
            this.mLastState = text;
            setSummary(this.mLastState);
        }
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return super.onGetDefaultValue(typedArray, i);
    }

    /* access modifiers changed from: protected */
    public void onSetInitialValue(boolean z, Object obj) {
        this.mLastState = this.CoreSettings.getStringValue((String) obj);
        if (VERSION.SDK_INT > 23 || z) {
            String str = this.mLastState;
            setText(str);
            setSummary(str);
            return;
        }
        String str2 = (String) obj;
        setText(str2);
        this.CoreSettings.setStringValue(str2);
        this.CoreSettings.executeCore(str2);
        setSummary(str2);
    }

    public void setPersistent(boolean z) {
        super.setPersistent(z);
    }

    public void setSummary(CharSequence charSequence) {
        super.setSummary(this.mPrSfSummary[0] + charSequence + this.mPrSfSummary[1]);
    }

    public boolean shouldDisableDependents() {
        try {
            return Integer.parseInt(this.CoreSettings.getStringValue()) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}