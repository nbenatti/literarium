package com.example.com.localDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.com.localDB.Book;
import com.example.com.localDB.BookDAO;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BookDAO getBookDAO();
}
