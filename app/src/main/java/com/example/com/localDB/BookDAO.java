package com.example.com.localDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(BookDB... bookDBS);
    @Update
    public void update(BookDB... bookDBS);
    @Delete
    public void delete(BookDB b);
    
    @Query("SELECT * FROM books WHERE userId = :userId")
    public List<BookDB> getAllBooks(String userId);
    @Query("SELECT * FROM books WHERE status = 1 AND userId = :userId")
    public List<BookDB> getAllSavedBooks(String userId);
    @Query("SELECT * FROM books WHERE status = 0 AND userId = :userId")
    public List<BookDB> getAllReceivedBooks(String userId);
    @Query("SELECT * FROM books WHERE status = 1 AND userId = :userId AND seen = 0")
    public List<BookDB> getUnseenSavedBooks(String userId);
}