

package com.mvpbase.data;


import android.content.Context;

import com.mvpbase.R;
import com.mvpbase.data.db.DbHelper;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.data.model.Notification;
import com.mvpbase.data.model.User;
import com.mvpbase.data.network.ApiHelper;
import com.mvpbase.data.network.model.DashboardResponse;
import com.mvpbase.data.network.model.Pagging;
import com.mvpbase.data.prefs.PreferencesHelper;
import com.mvpbase.utils.Constants;

import java.util.ArrayList;

public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    public enum ClearCatchType {
        CLEAR_ALL,
        CLEAR_RECENT_CLOSE,
        CLEAR_EMERGENCY_CONTACT
    }

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    public AppDataManager(Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }
    /*
     * DATA MANAGER METHOD START
     * */

    @Override
    public void logout() {
        mPreferencesHelper.clear();
    }


    private void getTop(String userId, ArrayList<DashboardResponse> responseArrayList, Callback<ArrayList<DashboardResponse>> onApiCallback) {
        mApiHelper.doGetMainLectureList(userId, "top", new Callback<ArrayList<Lecture>>() {
            @Override
            public void onSuccess(ArrayList<Lecture> baseResponse) {
                DashboardResponse dashboardResponse = new DashboardResponse();
                dashboardResponse.setType(Constants.DASH_TYPE_VIEWPAGER);
                dashboardResponse.setLecture(baseResponse);
                responseArrayList.add(dashboardResponse);
                getNew(userId, responseArrayList, onApiCallback);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });

    }

    private void getNew(String userId, ArrayList<DashboardResponse> responseArrayList, Callback<ArrayList<DashboardResponse>> onApiCallback) {
        mApiHelper.doGetMainLectureList(userId, "new", new Callback<ArrayList<Lecture>>() {
            @Override
            public void onSuccess(ArrayList<Lecture> baseResponse) {
                DashboardResponse dashboardResponse = new DashboardResponse();
                dashboardResponse.setType(Constants.DASH_TYPE_LIST);
                dashboardResponse.setHeader(mContext.getString(R.string.str_lbl_dashboard_header1));
                dashboardResponse.setHeaderType("new");
                dashboardResponse.setLecture(baseResponse);
                responseArrayList.add(dashboardResponse);
                getPopular(userId, responseArrayList, onApiCallback);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });
    }

    private void getPopular(String userId, ArrayList<DashboardResponse> responseArrayList, Callback<ArrayList<DashboardResponse>> onApiCallback) {
        mApiHelper.doGetMainLectureList(userId, "pop", new Callback<ArrayList<Lecture>>() {
            @Override
            public void onSuccess(ArrayList<Lecture> baseResponse) {
                DashboardResponse dashboardResponse = new DashboardResponse();
                dashboardResponse.setType(Constants.DASH_TYPE_LIST);
                dashboardResponse.setHeader(mContext.getString(R.string.str_lbl_dashboard_header2));
                dashboardResponse.setHeaderType("pop");
                dashboardResponse.setLecture(baseResponse);
                responseArrayList.add(dashboardResponse);
                onApiCallback.onSuccess(responseArrayList);
            }

            @Override
            public void onFailed(int code, String message) {
                onApiCallback.onFailed(code, message);
            }
        });


    }

    /*
     * DATA MANAGER METHOD END
     * */


    /**
     * API METHODS START
     */




    /* @Override
     public void doGetDashboardVideo(Pagging<DashboardResponse> pagging, Callback<Pagging> onApiCallback) {
         mApiHelper.doGetDashboardVideo(pagging, onApiCallback);
     }
 */


    @Override
    public void getNotificationList(Pagging<Notification> pagging, Callback<Pagging> onApiCallback) {
        mApiHelper.getNotificationList(pagging, onApiCallback);
    }


    @Override
    public void doGetMainLectureList(String userId, String uiFlag, Callback<ArrayList<Lecture>> onApiCallback) {
        mApiHelper.doGetMainLectureList(userId, uiFlag, onApiCallback);
    }


    /*=============API METHODS END===============*/


    /*
     * DATABASE METHODS START
     */

    @Override
    public long insertUser(com.mvpbase.data.db.room.tables.User user) {
        return mDbHelper.insertUser(user);
    }

    /*=============DATABASE METHODS END===============*/

    /**
     * PREFERENCE METHODS START
     */

    @Override
    public String get(String key, String defaultValue) {
        return mPreferencesHelper.get(key, defaultValue);
    }

    @Override
    public void set(String key, String value) {
        mPreferencesHelper.set(key, value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        mPreferencesHelper.setBoolean(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return mPreferencesHelper.getBoolean(key, defaultValue);
    }

    @Override
    public void setint(String key, int value) {
        mPreferencesHelper.setint(key, value);
    }

    @Override
    public int getint(String key, int defaultValue) {
        return mPreferencesHelper.getint(key, defaultValue);
    }

    @Override
    public void clear() {
        mPreferencesHelper.clear();
    }

    @Override
    public void remove(String key) {
        mPreferencesHelper.remove(key);
    }

    @Override
    public void setAppPref(String key, String value) {
        mPreferencesHelper.setAppPref(key, value);
    }

    @Override
    public String getAppPref(String key, String defaultValue) {
        return mPreferencesHelper.getAppPref(key, defaultValue);
    }

    @Override
    public void setBooleanAppPref(String key, boolean value) {
        mPreferencesHelper.setBooleanAppPref(key, value);
    }

    @Override
    public boolean getBooleanAppPref(String key, boolean defaultValue) {
        return mPreferencesHelper.getBooleanAppPref(key, defaultValue);
    }

    @Override
    public void setLong(String key, long value) {
        mPreferencesHelper.setLong(key, value);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return mPreferencesHelper.getLong(key, defaultValue);
    }

    @Override
    public void saveUserToPref(User user) {
        mPreferencesHelper.saveUserToPref(user);
    }

    @Override
    public User getUserFromPref() {
        return mPreferencesHelper.getUserFromPref();
    }

    /*=============PREFERENCE METHODS END===============*/

}
