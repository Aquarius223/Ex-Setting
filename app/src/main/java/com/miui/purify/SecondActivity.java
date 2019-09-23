package com.miui.purify;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;

import java.io.File;

import miui.app.AlertDialog;
import miui.os.SystemProperties;
import miui.preference.PreferenceActivity;


public class SecondActivity extends PreferenceActivity {

    public int getIDinternalXml(String str) {
        return getResources().getIdentifier(str, "xml", MyApplication.getContext().getPackageName());
    }

    @Override
    public void onCreate(Bundle bundle) {
        setTheme(miui.R.style.Theme_Light);
        SecondActivity.super.onCreate(bundle);
        initPreference();
    }

    private void initPreference() {
        addPreferencesFromResource(getIDinternalXml(getIntent().getStringExtra("key")));
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        PreferenceScreen screen = null;
        try {
            screen = (PreferenceScreen) preference;
            if (screen.getIntent() == null && screen.getKey() != null) {
                final File file = new File(Utils.getAssetsCacheFile(preference.getKey() + ".sh"));
                if (file.exists()) {
                    new AlertDialog.Builder(this)
                            .setMessage("是否执行" + preference.getTitle())
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Utils.execShell("sh " + file.getAbsolutePath());
                                }
                            }).show();
                }
            }
        } catch (Exception e) {

        }

        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    public String getName() {
        return SecondActivity.class.getName();
    }

}
