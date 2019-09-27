package com.mvpbase.data.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mvpbase.data.db.room.daos.UserAccess;
import com.mvpbase.data.db.room.tables.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabaseHandler extends RoomDatabase {
    public abstract UserAccess getUserDao();

    private static volatile AppDatabaseHandler INSTANCE;


   public static AppDatabaseHandler getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabaseHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabaseHandler.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
