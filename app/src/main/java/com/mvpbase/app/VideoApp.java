package com.mvpbase.app;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.mvpbase.data.AppDataManager;
import com.mvpbase.data.DataManager;
import com.mvpbase.data.db.AppDbHelper;
import com.mvpbase.data.network.AppApiHelper;
import com.mvpbase.data.prefs.AppPreferencesHelper;

/**
 * Created by etech3 on 21/11/17.
 */

public class VideoApp extends MultiDexApplication {
    public static Context appContext;
    private static DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        dataManager = new AppDataManager(this, new AppDbHelper(), new AppPreferencesHelper(this), new AppApiHelper());

    }

    public static DataManager getDataManager() {
        return dataManager;
    }


}
