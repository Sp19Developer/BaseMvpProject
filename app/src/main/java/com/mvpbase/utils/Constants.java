package com.mvpbase.utils;

import android.os.Environment;

import com.mvpbase.BuildConfig;

/**
 * Created by etech10 on 17/6/17.
 */

public class Constants {

    public static final int SUCCESS_CODE = 1;
    public static final String SUCCESS_CODE_STRING = "success";
    public static final int FAIL_CODE = 0;
    public static final int CANCEL_CODE = 2;
    public static final int FAIL_INTERNET_CODE = 3;

    public static final String BUILD_VERSION = "9";

    public static final String RES_MSG_KEY = "description";
    public static final String RES_CODE_KEY = "result";
    public static final String RES_OBJ_KEY = "data";


    public static int PERMISSION_CODE = 101;


    public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/VideoApp";
    public static String DIR_MEDIA = APP_HOME + "/media/";

    public static final String INTENT_NOTIFFICATION_TYPE = "notification_Type";
    public static final String INTENT_FILTER_RECEIVER_NOTIFICATION = BuildConfig.APPLICATION_ID + "." + "CUSTOM_NOTIFICATION";


    public static final String DASH_TYPE_VIEWPAGER = "viewpager";
    public static final String DASH_TYPE_LIST = "list";

    public static final String DASH_TYPE_HEADER = "header1";

}


