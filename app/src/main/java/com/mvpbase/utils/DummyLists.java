package com.mvpbase.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvpbase.R;
import com.mvpbase.app.VideoApp;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.data.model.Notification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DummyLists {

    public static ArrayList<Notification> getNotificationList() {
        ArrayList<Notification> arrHomeItemList = new ArrayList<>();
        String jsonObject = null;
        jsonObject = getResponseObjectArray("notification");
        if (jsonObject != null) {
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Notification>>() {
                }.getType();
                List<Notification> posts = (List<Notification>) gson.fromJson(jsonObject, listType);

                arrHomeItemList.addAll(posts);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return arrHomeItemList;
    }



    public static String getResponseObjectArray(String arrName) {
        String filename = "response.json";
        String str = loadJSONFromAsset(VideoApp.appContext, filename);
        try {
            JSONObject jsonObject1 = new JSONObject(str);
            return jsonObject1.getJSONArray(arrName).toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int getImageResourceId(String imgName) {
        imgName = imgName.toLowerCase();
        if (imgName.contains("a0001")) {
            return R.drawable.a0001;
        } else if (imgName.contains("a0002")) {
            return R.drawable.a0002;
        } else if (imgName.contains("a0003")) {
            return R.drawable.a0003;
        } else if (imgName.contains("a0004")) {
            return R.drawable.a0004;
        } else if (imgName.contains("a0005")) {
            return R.drawable.a0005;
        } else if (imgName.contains("a0006")) {
            return R.drawable.a0006;
        } else if (imgName.contains("a0007")) {
            return R.drawable.a0007;
        } else if (imgName.contains("a0008")) {
            return R.drawable.a0008;
        } else if (imgName.contains("a0009")) {
            return R.drawable.a0009;
        } else {
            return R.drawable.a0010;
        }
    }

    public static ArrayList<Lecture> getTop(Context context) {
        ArrayList<Lecture> top = new ArrayList<>();
        try {
            String jsonObject = new JSONArray(getResponseObjectArray("dashboard")).getJSONObject(0).getJSONArray("lecture").toString();
            if (jsonObject != null) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Lecture>>() {
                }.getType();
                List<Lecture> posts = gson.fromJson(jsonObject, listType);
                top.addAll(posts);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return top;
    }

    public static ArrayList<Lecture> getNew(Context context) {
        ArrayList<Lecture> top = new ArrayList<>();
        try {
            String jsonObject = new JSONArray(getResponseObjectArray("dashboard")).getJSONObject(1).getJSONArray("lecture").toString();
            if (jsonObject != null) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Lecture>>() {
                }.getType();
                List<Lecture> posts = gson.fromJson(jsonObject, listType);
                top.addAll(posts);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return top;
    }

    public static ArrayList<Lecture> getPop(Context context) {
        ArrayList<Lecture> top = new ArrayList<>();
        try {
            String jsonObject = new JSONArray(getResponseObjectArray("dashboard")).getJSONObject(2).getJSONArray("lecture").toString();
            if (jsonObject != null) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Lecture>>() {
                }.getType();
                List<Lecture> posts = gson.fromJson(jsonObject, listType);
                top.addAll(posts);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return top;
    }

}
