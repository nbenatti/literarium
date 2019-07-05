package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.literarium.R;
import com.example.com.literarium.ShowBookActivity;

import java.util.List;

/**
 * deletes a list of bookDB
 */
public class DeleteBookTask extends AsyncTask {

    private static final String TAG = DeleteBookTask.class.getSimpleName();

    private Context ctx;
    private LocalDatabase db;
    private BookDAO bookDao;
    private List<BookDB> booksToBeDeleted;

    private ShowBookActivity act;

    private SharedPreferences sharedPreferences;

    public DeleteBookTask(Context ctx, List<BookDB> booksToBeDeleted) {
        this.ctx = ctx;
        this.booksToBeDeleted = booksToBeDeleted;

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

        for(BookDB bookDB : booksToBeDeleted) {

            bookDao.delete(bookDB);
        }

        dumpDb();

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        ShowBookActivity act = (ShowBookActivity)ctx;

        act.handleBookDeletionSuccess();
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
            Log.d("LOCAL_DB", bookDB.toString());
    }
}
