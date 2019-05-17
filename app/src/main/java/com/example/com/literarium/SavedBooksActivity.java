package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.com.localDB.BookDB;

import java.util.ArrayList;

public class SavedBooksActivity extends Activity {

    private ListView sbList; // listview component
    private SavedBookListAdapter sbAdapter;
    private ArrayList<BookDB> bookListData; // saved books list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedbooks_layout);

        sbList = findViewById(R.id.savedbookslist);

        bookListData = new ArrayList<BookDB>();

        sbAdapter = new SavedBookListAdapter(this, R.layout.savedbook_item, bookListData);
        sbList.setAdapter(sbAdapter);

        /*sbList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the book
                Intent showBook = new Intent(this, ShowBookActivity.class);

                Bundle bookData = new Bundle();
                bookData.putParcelable(getString(R.string.book_data), bookListData.get(i));

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });*/

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
