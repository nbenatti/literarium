package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.com.localDB.BookDB;
import com.example.com.localDB.DbUtils;

import java.util.ArrayList;

public class SavedBooksActivity extends Activity {

    private Context ctx;

    private ListView sbList; // listview component
    private SavedBookListAdapter sbAdapter;
    private ArrayList<BookDB> bookListData; // saved books list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedbooks_layout);

        Log.d("SavedBooksActivity", "oncreate()");

        ctx = this;

        sbList = findViewById(R.id.savedBooksList);

        bookListData = new ArrayList<BookDB>();

        sbAdapter = new SavedBookListAdapter(this, R.layout.savedbook_item, bookListData);
        sbList.setAdapter(sbAdapter);

        sbList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the book
                Intent showBook = new Intent(ctx, ShowBookActivity.class);

                Bundle bookData = new Bundle();
                bookData.putParcelable(getString(R.string.book_data), DbUtils.convertSingleBookDbToBook(bookListData.get(i)));
                bookData.putString(getString(R.string.book_type), "saved");

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });

        sbList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeSavedBooks();
                return true;
            }
        });

        /*FetchSavedBooksTask fetchSavedBooksTask = new FetchSavedBooksTask(this);
        fetchSavedBooksTask.execute();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SavedBooksActivity", "onresume()");

        // refresh the list
        FetchSavedBooksTask fetchSavedBooksTask = new FetchSavedBooksTask(this);
        fetchSavedBooksTask.execute();
    }

    public void populate(ArrayList<BookDB> bookList) {

        bookListData.clear();
        bookListData.addAll(bookList);
        sbAdapter.notifyDataSetChanged();
    }

    public void removeSavedBooks() {

        Log.d("SavedBooksActivity", "LONG PRESS");
    }

    public void goToSearchLayout(View v) {

        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    public void goToUserLayout(View v) {

        Intent i = new Intent(this, UserShowActivity.class);
        startActivity(i);
    }

}
