package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.literarium.Globals;
import com.example.com.literarium.R;
import com.example.com.literarium.ShowBookActivity;
import com.example.com.parsingData.enumType.BookType;

import java.util.ArrayList;
import java.util.List;

/**
 * saves the book into the local db
 */
public class SaveBookTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private List<BookDB> booksToBeSaved;

    private ShowBookActivity act;

    private SharedPreferences sharedPreferences;

    public SaveBookTask(Context ctx, List<com.example.com.parsingData.parseType.Book> b, BookType bt) {

        this.ctx = ctx;

        sharedPreferences = Globals.getSharedPreferences(ctx);

        if(ctx instanceof ShowBookActivity) {
            act = (ShowBookActivity) ctx;
        }
        else
            act = null;

        booksToBeSaved = new ArrayList<>();

        boolean seen = false, status = false;

        if(bt == BookType.RECEIVED_BOOK) {
            seen = false;
            status = true;
        }else if (bt == BookType.SAVED_BOOK) {
            seen = true;
            status = false;
        }

        // convert book objects to be saved in the db
        for(com.example.com.parsingData.parseType.Book book : b) {
            booksToBeSaved.add(new BookDB(book.getId(),
                    String.valueOf(sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1)),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getImageUrl(),
                    book.getPublicationYear(),
                    book.getPublisher(),
                    book.getDescription(),
                    book.getAverageRating(),
                    book.getNumPages(),
                    book.getAuthor().getId(),
                    book.getAuthor().getName(),
                    seen,
                    status));
        }
    }

    @Override
    protected void onPreExecute() {
        createDb();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for(BookDB runnerBookDB : booksToBeSaved)
            insertBook(runnerBookDB);

        closeDb();

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        //notify the calling activity with the operation's status
        if(act != null)
            act.handleBookSavingSuccess();
    }

    private void createDb() {
        db = Room.databaseBuilder(ctx, LocalDatabase.class, "local-db").build();
        bookDao = db.getBookDAO();
    }

    private void closeDb() {
        db.close();
    }

    private void insertBook(BookDB b) {

        bookDao.insert(b);

        List<BookDB> res = bookDao.getAllBooks(String.valueOf(sharedPreferences.getInt(ctx.getString(R.string.user_id_setting), -1)));
        for(BookDB bookDB : res)
            Log.d("LOCAL_DB", bookDB.toString());
    }
}
