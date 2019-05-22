package com.example.com.localDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.com.literarium.ShowBookActivity;
import com.example.com.parsingData.parseType.Book;

import java.util.List;

public class ModifyBookTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private List<Book> booksToBeModified;

    private ShowBookActivity act;

    private SharedPreferences sharedPreferences;

    public ModifyBookTask(Context ctx, List<Book> booksToBeModified) {
        this.ctx = ctx;
        this.booksToBeModified = booksToBeModified;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        return null;
    }
}
