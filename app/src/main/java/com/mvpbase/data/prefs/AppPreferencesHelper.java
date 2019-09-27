
package com.mvpbase.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.mvpbase.data.model.User;


public class AppPreferencesHelper implements PreferencesHelper {

    public static final String PREF_USER_ID = "user_id";


    private static String prefFile = "Jibudo_Pref";
    private static String prefFileApp = "Jibudo_Pref_App";

    private final SharedPreferences mPrefs;
    private final SharedPreferences mPrefsApp;


    public AppPreferencesHelper(Context context) {

        mPrefs = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        mPrefsApp = context.getSharedPreferences(prefFileApp, Context.MODE_PRIVATE);
    }

    @Override
    public String get(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    @Override
    public void set(String key, String value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.commit();

    }

    @Override
    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mPrefs.getBoolean(key, defaultValue);
    }

    @Override
    public void setint(String key, int value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    @Override
    public int getint(String key, int defaultValue) {
        return mPrefs.getInt(key, defaultValue);
    }

    @Override
    public void clear() {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void remove(String key) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(key);
        editor.commit();
    }

    @Override
    public void setAppPref(String key, String value) {
        SharedPreferences.Editor editor = mPrefsApp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getAppPref(String key, String defaultValue) {
        return mPrefsApp.getString(key, defaultValue);
    }

    @Override
    public void setBooleanAppPref(String key, boolean value) {
        SharedPreferences.Editor editor = mPrefsApp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean getBooleanAppPref(String key, boolean defaultValue) {
        return mPrefsApp.getBoolean(key, defaultValue);
    }

    @Override
    public void saveUserToPref(User user) {
        set(PREF_USER_ID,user.getUserId());
    }

    @Override
    public User getUserFromPref() {
        User user=new User();
        user.setUserId(get(PREF_USER_ID,""));
        return user;
    }

    @Override
    public void setLong(String key,long value) {
        SharedPreferences.Editor editor = mPrefsApp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mPrefs.getLong(key, defaultValue);
    }
}
