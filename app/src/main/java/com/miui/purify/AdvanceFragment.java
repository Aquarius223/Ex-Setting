package com.miui.purify;


import android.os.Bundle;

public class AdvanceFragment extends AppBase {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.advance_settings);
    }
}
