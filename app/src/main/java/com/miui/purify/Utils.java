package com.miui.purify;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.execution.Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;

public class Utils {
   public static void execCommond(Command command) {
        try {
            if (RootShell.isAccessGiven()) {
                RootShell.getShell(true).add(command);
            } else {
                RootShell.isAccessGiven();
            }
        } catch (Exception e) {

        }
    }

    public static void execShell(String str) {
        showToast(str);
        try {
            if (RootShell.isAccessGiven()) {
                Command command = new Command(0, str) {
                    @Override
                    public void commandOutput(int id, String line) {
                        super.commandOutput(id, line);
                        Toast mToast = null;
                        if (mToast == null) {
                            mToast = Toast.makeText(MyApplication.getContext(), null, Toast.LENGTH_SHORT);
                            mToast.setText(line);
                        } else {
                            mToast.setText(line);
                        }
                        mToast.show();
                    }

                    @Override
                    public void commandTerminated(int id, String reason) {
                        super.commandTerminated(id, reason);
                    }

                    @Override
                    public void commandCompleted(int id, int exitcode) {
                        super.commandCompleted(id, exitcode);
                    }
                };
                RootShell.getShell(true).add(command);
            } else {
                RootShell.isAccessGiven();
            }
        } catch (Exception e) {

        }

    }

    private static void showToast(String str) {
        File debug = new File(MyApplication.getContext().getDataDir() + File.separator + "debug.purify");
        if (debug.exists()) {
            Toast mToast = Toast.makeText(MyApplication.getContext(), null, Toast.LENGTH_SHORT);
            mToast.setText(str);
            mToast.show();
        }
    }

    public static void execShellWithoutInfo(String str) {
        try {
            if (RootShell.isAccessGiven()) {
                Command command = new Command(0, str);
                RootShell.getShell(true).add(command);
            } else {
                RootShell.isAccessGiven();
            }
        } catch (Exception e) {

        }

    }


    public static String getAssetsCacheFile(String fileName) {
        Context context = MyApplication.getContext();
        File cacheFile = new File(context.getCacheDir(), fileName);
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
        File scriptFile = new File(cacheFile.getParent());
        if (!scriptFile.exists()) {
            scriptFile.mkdirs();
        }

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cacheFile.setExecutable(true);
        return cacheFile.getAbsolutePath();
    }

    public static String readFile(String file) {
        String pathname = file; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String context = "";
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                context = context + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }


}
