package com.mvpbase.data.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvpbase.app.VideoApp;
import com.mvpbase.data.DataManager.Callback;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.data.model.Notification;
import com.mvpbase.data.network.apiHelper.RestHelper;
import com.mvpbase.data.network.apiHelper.RestResponse;
import com.mvpbase.data.network.model.Pagging;
import com.mvpbase.data.prefs.AppPreferencesHelper;
import com.mvpbase.utils.AppUtils;
import com.mvpbase.utils.DummyLists;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AppApiHelper implements ApiHelper {


    public static final String BASE_URL = "https://api.demo.com";/*live*/
    private static final String BASE_URL_ADDITITON = "/api";/*live*/


    private static final String API_MAIN_LECTURE_LIST = BASE_URL_ADDITITON + "/lecture-list-main";
    private static final String API_NOTIFICATION_LIST = BASE_URL_ADDITITON + "/push-list";

    private Gson gson = new Gson();


    public AppApiHelper() {
        RestHelper.DEFAULT_BASE_URL = BASE_URL;
    }


    @Override
    public void getNotificationList(Pagging<Notification> pagging, Callback<Pagging> onApiCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", VideoApp.getDataManager().get(AppPreferencesHelper.PREF_USER_ID, null));
        new RestHelper.Builder()
                .setUrl(API_NOTIFICATION_LIST)
                .setParams(params)
                .setCallBack(new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {

                        final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (jsonObject != null) {
                            try {

                                Pagging<Notification> newPagging = new Pagging<>();
                                newPagging.setHasMore(false);
                                JSONArray jsonObj = jsonObject.getJSONArray("notification");
                                ArrayList<Notification> categories = gson.fromJson(jsonObj.toString(), new TypeToken<ArrayList<Notification>>() {
                                }.getType());
                                newPagging.addItemToList(categories);
                                onApiCallback.onSuccess(newPagging);

                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }

                    }
                })
                .build()
//                .sendRequest()
        ;
        Pagging<Notification> newPagging = new Pagging<Notification>();
        newPagging.setHasMore(false);
        ArrayList<Notification> incidents = DummyLists.getNotificationList();
        if (incidents != null && incidents.size() > 0) {
            newPagging.addItemToList(incidents);
            newPagging.setPageNumber(pagging.getPageNumber() + 1);
        }
        onApiCallback.onSuccess(newPagging);
    }


    @Override
    public void doGetMainLectureList(String userId, String uiFlag, Callback<ArrayList<Lecture>> onApiCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("ui_flag", uiFlag);
        new RestHelper.Builder()
                .setUrl(API_MAIN_LECTURE_LIST)
                .setParams(params)
                .setCallBack(new RestHelper.RestHelperCallback() {
                    @Override
                    public void onRequestCallback(int code, String message, RestResponse restResponse) {

                        final JSONObject jsonObject = AppUtils.checkResponse(code, message, restResponse);
                        if (jsonObject != null) {
                            try {

                                JSONArray jsonObj = jsonObject.getJSONArray("lecture");
                                ArrayList<Lecture> categories = gson.fromJson(jsonObj.toString(), new TypeToken<ArrayList<Lecture>>() {
                                }.getType());
                                onApiCallback.onSuccess(categories);

                            } catch (Exception e) {
                                onApiCallback.onFailed(code, message);
                                e.printStackTrace();
                            }
                        } else {
                            onApiCallback.onFailed(code, message);
                        }

                    }
                })
                .build()
//                .sendRequest()
        ;

        ArrayList<Lecture> list = null;
        if (uiFlag.equals("top")) {
            list = DummyLists.getTop(VideoApp.appContext);
            ;
        } else if (uiFlag.equals("new")) {
            list = DummyLists.getNew(VideoApp.appContext);
        } else if (uiFlag.equals("pop"))
            list = DummyLists.getPop(VideoApp.appContext);
        onApiCallback.onSuccess(list);
    }

}

