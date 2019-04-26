package com.example.com.localDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    public void insert(Book... items);
    @Update
    public void update(Book... items);
    @Delete
    public void delete(Book item);

    @Query("SELECT * FROM book")
    public List<Book> getItems();
}