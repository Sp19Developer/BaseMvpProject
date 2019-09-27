package com.mvpbase.data.db.room.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mvpbase.data.db.room.tables.User;

import java.util.List;

@Dao
public interface UserAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertUser(User user);

    @Query("SELECT * FROM User WHERE userid = :userId LIMIT 1")
    User selectUserFromUserId(String userId);


    @Query("SELECT * FROM User")
    List<User> getAllUser();


    @Delete
    void deleteHistory(User user);

}
