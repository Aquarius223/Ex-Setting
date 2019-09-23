package com.miui.purify.prefence;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class purifyList extends ListPreference {
    public purifyCoreSettings CoreSettings = new purifyCoreSettings();
    private String mLastState;

    public purifyList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.CoreSettings.initialization(context, attributeSet, getKey());
    }

    public boolean getDependents(String str) {
        try {
            return Integer.parseInt(str) <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        String value = getValue();
        if (this.mLastState != value) {
            notifyDependencyChange(getDependents(value));
            this.CoreSettings.setStringValue(value);
            this.CoreSettings.executeCore(value);

            this.mLastState = value;
            setSummary(getEntry());
        }
    }

    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    public void onSetInitialValue(boolean z, Object obj) {
        this.mLastState = this.CoreSettings.getStringValue((String) obj);
        if (VERSION.SDK_INT > 23 || z) {
            setValue(this.mLastState);
            setSummary(getEntry());
            return;
        }
        setValue(this.mLastState);
        this.CoreSettings.setStringValue(this.mLastState);
//        this.CoreSettings.sendIntent();
        setSummary(getEntry());
    }
}