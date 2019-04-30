package com.example.com.localDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    public void insert(localDB.Book... books);
    @Update
    public void update(localDB.Book... books);
    @Delete
    public void delete(localDB.Book book);
    
    @Query("SELECT * FROM book")
    public List<localDB.Book> getAllBooks();
    @Query("SELECT * FROM book WHERE status = 'saved'")
    public List<localDB.Book> getSavedBooks();
    @Query("SELECT * FROM book WHERE status = 'sent'")
    public List<localDB.Book> getSentBooks();
}