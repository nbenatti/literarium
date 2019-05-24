package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.literarium.R;
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

    private boolean status;
    private boolean seen;

    public ModifyBookTask(Context ctx, List<Book> booksToBeModified, boolean status, boolean seen) {
        this.ctx = ctx;
        this.booksToBeModified = booksToBeModified;
        this.status = status;
        this.seen = seen;

        // get preferences
        sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        createDb();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        /*for(Book runner : booksToBeModified) {

            BookDB bookDB = DbUtils.convertBookToBookDB(runner, sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1), status, seen);

            bookDao.update(bookDB);
        }*/

        bookDao.toggleSeenBooks(String.valueOf(sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1)));

        dumpDb();

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
    }

    private void createDb() {
        db = Room.databaseBuilder(ctx, LocalDatabase.class, "local-db").build();
        bookDao = db.getBookDAO();
    }

    private void closeDb() {
        db.close();
    }

    private void dumpDb() {
        List<BookDB> res = bookDao.getAllBooks(String.valueOf(sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1)));
        for(BookDB bookDB : res)
            Log.d("ModifyBookTask", bookDB.toString());
    }
}
