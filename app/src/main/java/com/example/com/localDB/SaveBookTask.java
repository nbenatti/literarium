package com.example.com.localDB;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.com.literarium.Globals;
import com.example.com.literarium.ShowBookActivity;

import java.util.List;

/**
 * saves the book into the local db
 */
public class SaveBookTask extends AsyncTask {

    private Context ctx;

    private LocalDatabase db;
    private BookDAO bookDao;

    private List<Book> booksToBeSaved;

    public SaveBookTask(Context ctx, List<com.example.com.parsingData.parseType.Book> b) {

        this.ctx = ctx;

        // convert book objects to be saved in the db
        for(com.example.com.parsingData.parseType.Book book : b) {
            booksToBeSaved.add(new Book(book.getId(),
                    String.valueOf(Globals.getInstance().getUserLocalData().getUserId()),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getImageUrl(),
                    book.getPublicationYear(),
                    book.getPublisher(),
                    book.getDescription(),
                    book.getAverageRating(),
                    book.getNumPages(),
                    book.getAuthor().getId(),
                    book.getAuthor().getName()));
        }
    }

    @Override
    protected void onPreExecute() {
        createDb();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        for(Book runnerBook : booksToBeSaved)
            insertBook(runnerBook);

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

    private void insertBook(Book b) {

        bookDao.insert(b);
        List<Book> res = bookDao.getAllBooks(String.valueOf(Globals.getInstance().getUserLocalData().getUserId()));
        for(Book book : res)
            Log.d("LOCAL_DB", book.toString());
    }
}
