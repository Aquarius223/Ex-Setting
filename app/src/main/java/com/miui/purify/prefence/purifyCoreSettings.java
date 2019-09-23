package com.miui.purify.prefence;

import android.content.Context;
import android.util.AttributeSet;

import com.miui.purify.MyApplication;
import com.miui.purify.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class purifyCoreSettings {
    private File purifySettingXml = new File(MyApplication.getContext().getDataDir(), "purifySetting.xml");
    private AttributeSet attrs;
    private String mKey;
    private String reset;


    public void executeCore(String val) {
        String shellpath = Utils.getAssetsCacheFile("script" + File.separator + this.mKey + ".sh");
        Utils.execShell("sh " + shellpath + "  \"" + val+"\"");
    }

    public String getAttributeValue(String str) {
        return this.attrs.getAttributeValue(null, str);
    }

    public void initialization(Context context, AttributeSet attributeSet, String str) {
        this.attrs = attributeSet;
        this.mKey = str;
        this.reset = getAttributeValue("reset");
    }

    public Integer getIntegerValue(int i) {
        int configValue = 999;
        try {
            if (purifySettingXml.exists()) {
                FileInputStream fis = new FileInputStream(purifySettingXml);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("=") != -1) {
                        String[] lines = line.split("=");
                        String key = lines[0];
                        String value = "";
                        if (lines.length > 1) {
                            value = lines[1];
                        }
                        if (mKey.equals(key)) {
                            configValue = Integer.valueOf(value);
                        }
                    }
                }
                br.close();
                fis.close();
            }
        } catch (Exception e) {

        }

        if (999 == configValue) {
            setIntegerValue(i);
            return getIntegerValue(i);
        }
        return configValue;
    }

    public void setIntegerValue(int i) {
        try {
            File file = purifySettingXml;
            if (!file.exists()) {
                file.createNewFile();//创建文件
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String lineTxt = null;
            Map<String, String> map = new HashMap<String, String>();
            while ((lineTxt = br.readLine()) != null) {
                String[] lines = lineTxt.split("=");
                if (lines.length > 1) {
                    map.put(lines[0], lineTxt.split("=")[1]);
                } else {
                    map.put(lines[0], "");
                }
            }
            br.close();
            String value = String.valueOf(i);
            if (this.reset != null) {
                value = value + "=" + this.reset;
            }
            map.put(this.mKey, value);

            FileWriter fileWritter = new FileWriter(file);
            for (String key : map.keySet()) {
                fileWritter.write(key + "=" + map.get(key) + "\n");
            }
            fileWritter.close();
            Utils.execShell("chmod 644 " + purifySettingXml);
        } catch (Exception e) {
        }
    }

    public String getStringValue(String defvalue) {
        String configValue = null;

        try {
            if (purifySettingXml.exists()) {
                FileInputStream fis = new FileInputStream(purifySettingXml);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("=") != -1) {
                        String[] lines = line.split("=");
                        String key = lines[0];
                        String value = "";
                        if (lines.length > 1) {
                            value = lines[1];
                        }
                        if (mKey.equals(key)) {
                            configValue = value;
                        }
                    }
                }
                br.close();
                fis.close();
            }
        } catch (Exception e) {
        }

        if (null == configValue) {
            setStringValue(defvalue);
            return getStringValue(defvalue);
        }
        return configValue;
    }

    public String getStringValue() {
        return "";
    }

    public void setStringValue(String value) {
        try {
            File file = purifySettingXml;
            if (!file.exists()) {
                file.createNewFile();//创建文件
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String lineTxt = null;
            Map<String, String> map = new HashMap<String, String>();
            while ((lineTxt = br.readLine()) != null) {
                String[] lines = lineTxt.split("=");
                if (lines.length > 1) {
                    map.put(lines[0], lineTxt.split("=")[1]);
                } else {
                    map.put(lines[0], "");
                }
            }
            br.close();
            if (this.reset != null) {
                value = value + "=" + this.reset;
            }
            map.put(this.mKey, value);

            FileWriter fileWritter = new FileWriter(file);
            for (String key : map.keySet()) {
                fileWritter.write(key + "=" + map.get(key) + "\n");
            }
            fileWritter.close();
            Utils.execShell("chmod 644 " + purifySettingXml);
        } catch (Exception e) {
        }
    }

    public int IDtoID(String str) {
        return MyApplication.getContext().getResources().getIdentifier(str, "id", MyApplication.getContext().getPackageName());
    }

    public int LayoutToID(String str) {
        return MyApplication.getContext().getResources().getIdentifier(str, "layout", MyApplication.getContext().getPackageName());
    }

    public int StyleToID(String str) {
        return MyApplication.getContext().getResources().getIdentifier(str, "style", MyApplication.getContext().getPackageName());
    }

    public String[] initSummary(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        if (charSequence2.indexOf("%s") == -1) {
            charSequence2 = charSequence2 + " %s";
        }
        return new String[]{charSequence2.substring(0, charSequence2.indexOf("%s")), charSequence2.substring(charSequence2.indexOf("%s") + 2)};
    }

    public int getAttributeInt(String str, int i) {
        return this.attrs.getAttributeIntValue(null, str, i);
    }

}
