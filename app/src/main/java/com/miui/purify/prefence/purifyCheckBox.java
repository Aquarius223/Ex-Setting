package com.miui.purify.prefence;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;

public class purifyCheckBox extends CheckBoxPreference {
    public purifyCoreSettings CoreSettings = new purifyCoreSettings();
    private int mDefaultValue;


    public purifyCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.CoreSettings.initialization(context, attributeSet, getKey());
    }

    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        int i2 = 0;
        if (typedArray.getBoolean(i, false)) {
            i2 = 1;
        }
        this.mDefaultValue = i2;
        return Integer.valueOf(i2);
    }

    public void onSetInitialValue(boolean z, Object obj) {
        setChecked(this.CoreSettings.getIntegerValue(this.mDefaultValue).intValue() != 0);
    }


    public void setChecked(boolean z) {
        int i = 1;
        super.setChecked(z);
        int i2 = z ? 1 : 0;
        if (i2 != this.CoreSettings.getIntegerValue(this.mDefaultValue).intValue()) {
            if (!z) {
                i = 0;
            }
            this.CoreSettings.setIntegerValue(i);
            this.CoreSettings.executeCore(String.valueOf(i2));
        }
    }

}
