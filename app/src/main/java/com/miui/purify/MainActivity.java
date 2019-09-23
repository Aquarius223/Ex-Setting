package com.miui.purify;


import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

import java.io.File;

import miui.app.AlertDialog;
import miui.os.SystemProperties;
import miui.preference.PreferenceActivity;

public class MainActivity extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        setTheme(miui.R.style.Theme_Light);
        super.onCreate(savedInstanceState);
        Utils.getAssetsCacheFile("Enveriment.sh");
        Utils.execShellWithoutInfo("sh " + Utils.getAssetsCacheFile("init.sh"));

        while (true) {
            if (Utils.readFile("/proc/stat").contains(Utils.readFile("/data/user/0/com.miui.purify/stat"))) {
                initPreference();
                break;
            }
        }
    }

    private void initPreference() {
        int XML = R.xml.advance_settings;
        addPreferencesFromResource(XML);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        PreferenceScreen screen = null;
        try {
            screen = (PreferenceScreen) preference;
            if (screen.getIntent() == null && screen.getKey() != null) {
                final File file = new File(Utils.getAssetsCacheFile("script" + File.separator + preference.getKey() + ".sh"));
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

}
