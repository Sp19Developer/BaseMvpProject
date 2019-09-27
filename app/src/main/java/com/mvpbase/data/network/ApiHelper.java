package com.mvpbase.data.network;

import com.mvpbase.data.DataManager.Callback;
import com.mvpbase.data.model.Lecture;
import com.mvpbase.data.model.Notification;
import com.mvpbase.data.network.model.Pagging;

import java.util.ArrayList;

public interface ApiHelper {


    void doGetMainLectureList(String userId, String uiFlag, Callback<ArrayList<Lecture>> onApiCallback);
    void getNotificationList(Pagging<Notification> pagging, Callback<Pagging> onApiCallback);

}
