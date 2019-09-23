package com.miui.purify;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AppBase extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        if (null != parent) {
            View listView = parent.findViewById(android.R.id.list);
            if (null != listView) {
                listView.setPadding(0, listView.getPaddingTop(), 0, listView.getPaddingBottom());
            }
        }
        return parent;
    }
}
