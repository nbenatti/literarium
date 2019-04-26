package com.example.com.literarium;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.com.localDb.Book;
import com.example.com.localDb.BookDAO;
import com.example.com.localDb.LocalDatabase;

import java.util.List;

/**
 * saves the book into the local db
 */
public class SaveBookTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private Book bookToSave;

    public SaveBookTask(Context ctx, Book b) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        createDb();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        insertBook(bookToSave);

        closeDb();

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        //notify the calling activity with the operation's status

    }

    private void createDb() {
        Context context = ctx;
        db = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();
        bookDao = db.getBookDAO();
    }

    private void closeDb() {
        db.close();
    }

    private void insertBook(Book b) {

        bookDao.insert(b);
        List<Book> res = bookDao.getItems();
        Log.d("LOCAL_DB", res.get(0).toString());
    }
}
