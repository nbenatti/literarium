package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class PopulateDbTask extends AsyncTask {

    private LocalDatabase db;
    private BookDAO bookDao;

    private Context ctx;

    public PopulateDbTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        createDb();
        insertBook(new Book(10, "la Bibbia", "dicono sia un bel libro", 1000, false));

        return null;
    }

    // test local db
    private void createDb() {
        Context context = ctx;
        db = Room.inMemoryDatabaseBuilder(context, LocalDatabase.class).build();
        bookDao = db.getBookDAO();
    }

    private void closeDb(LocalDatabase db) {
        db.close();
    }

    private void insertBook(Book b) {

        bookDao.insert(b);
        List<Book> res = bookDao.getItems();
        Log.d("LOCAL_DB", res.get(0).toString());
    }
}
