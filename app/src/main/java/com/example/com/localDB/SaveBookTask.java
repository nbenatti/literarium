package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.com.literarium.Globals;
import com.example.com.literarium.ShowBookActivity;
import com.example.com.localDB.*;
import com.example.com.localDB.Book;

import java.util.List;

/**
 * saves the book into the local db
 */
public class SaveBookTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private Book bookToSave;

    public SaveBookTask(Context ctx, com.example.com.dataAcquisition.Book b) {

        this.ctx = ctx;

        // convert book object to be saved in the db
        bookToSave = new Book(b.getId(), Globals.getInstance().getUserLocalData().getUserId(), b.getTitle(), b.getDescription(), b.getNumPages(), false);
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
        ShowBookActivity act = (ShowBookActivity)ctx;
        act.handleBookSavingSuccess();
    }

    private void createDb() {
        Context context = ctx;
        db = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();
        bookDao = db.getBookDAO();
    }

    private void closeDb() {
        db.close();
    }

    private void insertBook(com.example.com.localDB.Book b) {

        bookDao.insert(b);
        List<com.example.com.localDB.Book> res = bookDao.getAllBooks();
        for(Book book : res)
            Log.d("LOCAL_DB", book.toString());
    }
}
