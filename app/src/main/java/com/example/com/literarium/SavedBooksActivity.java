package com.example.com.literarium;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;

public class SavedBooksActivity extends Activity {

    private ListView sbList; // saved books list component
    private SavedBookListAdapter sbAdapter;
    private ArrayList<Book> bookList; // saved books list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedbooks_layout);

        sbList = findViewById(R.id.savedbookslist);

        bookList = new ArrayList<Book>();

        sbAdapter = new SavedBookListAdapter(this, R.layout.savedbook_item, bookList);
        sbList.setAdapter(sbAdapter);

    }

}
