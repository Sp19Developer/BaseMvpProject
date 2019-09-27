package com.mvpbase.utils;

import android.util.Log;

import java.io.File;

//@SuppressLint({ "SimpleDateFormat", "SdCardPath" })
public class Storage {

    public static void verifyDirPath(String dir) {

        try {
            File f_dir = new File(dir);
            if (!f_dir.exists()) {
                f_dir.mkdirs();
                Log.e("directories verify ", dir);
            }
            f_dir = null;
        } catch (Exception e) {
            Log.v("verifyDirPath", "error : ");
            e.printStackTrace();
            Log.e("directories not verify", e.toString());
        }

    }


}