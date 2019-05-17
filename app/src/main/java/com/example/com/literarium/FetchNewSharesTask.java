package com.example.com.literarium;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.com.localDB.BookDAO;
import com.example.com.localDB.BookDB;
import com.example.com.localDB.DbUtils;
import com.example.com.localDB.LocalDatabase;
import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;
import java.util.List;

public class FetchNewSharesTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private String userId;

    private SharedPreferences sharedPreferences;

    public FetchNewSharesTask(Context ctx) {
        this.ctx = ctx;

        // get preferences
        sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        userId = String.valueOf(sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1));

        // access the DB
        createDb();

        List<BookDB> unseenBookList = bookDao.getUnseenSavedBooks(userId);

        closeDb();

        return unseenBookList;
    }

    @Override
    protected void onPostExecute(Object o) {

        MainActivity act = (MainActivity)ctx;

        ArrayList<BookDB> dbBooks = (ArrayList<BookDB>)o;

        ArrayList<Book> books = (ArrayList<Book>) DbUtils.convertBookDBToBook(dbBooks);

        act.populate(books);
    }

    private void createDb() {
        db = Room.databaseBuilder(ctx, LocalDatabase.class, "local-db").build();
        bookDao = db.getBookDAO();
    }

    private void closeDb() {
        db.close();
    }
}