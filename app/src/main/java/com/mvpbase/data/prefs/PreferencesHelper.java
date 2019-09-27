
package com.mvpbase.data.prefs;


import com.mvpbase.data.model.User;

public interface PreferencesHelper {

    String get(String key, String defaultValue);

    void set(String key, String value);

    void setBoolean(String key, boolean value);

    boolean getBoolean(String key, boolean defaultValue);

    void setint(String key, int value);

    int getint(String key, int defaultValue);

    void setLong(String key,long value);

    long getLong (String key,long defaultValue);

    void clear();

    void remove(String key);

    void setAppPref(String key, String value);

    String getAppPref(String key, String defaultValue);

    void setBooleanAppPref(String key, boolean value);

    boolean getBooleanAppPref(String key, boolean defaultValue);

    void saveUserToPref(User user);

    User getUserFromPref();
}
