package com.example.com.localDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Book.class, User.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract BookDAO getBookDAO();
    public abstract UserDAO getUserDAO();
}
