package com.mvpbase.data.db;


import com.mvpbase.app.VideoApp;
import com.mvpbase.data.db.room.AppDatabaseHandler;
import com.mvpbase.data.db.room.tables.User;

/**
 * Created by janisharali on 08/12/16.
 */

public class AppDbHelper implements DbHelper {


    public AppDbHelper() {


    }

    @Override
    public long insertUser(User user) {
        return AppDatabaseHandler.getDatabase(VideoApp.appContext).getUserDao().insertUser(user);
    }
}
