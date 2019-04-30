package com.example.com.localDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public void insert(User... items);
    @Update
    public void update(User... items);
    @Delete
    public void delete(User item);

    @Query("SELECT * FROM user")
    public List<User> getItems();
}