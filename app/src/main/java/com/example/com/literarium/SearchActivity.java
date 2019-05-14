package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText keyword;

    private ArrayList<com.example.com.parsingData.parseType.Book> resultListData;

    private ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //getActionBar().hide();

        keyword = findViewById(R.id.search_bar);
        resultList = findViewById(R.id.resultList);

        resultListData = new ArrayList<>();
        BookListAdapter bookListAdapter = new BookListAdapter(this, R.layout.book_item, resultListData);
        resultList.setAdapter(bookListAdapter);
    }

    public void goToBookSHow(View v) {

        Intent i = new Intent(this, ShowBookActivity.class);
        startActivity(i);
    }

    public void performSearch(View v) {

        String keyword = this.keyword.getText().toString();

        SearchBooksTask searchBooksTask = new SearchBooksTask(this, keyword);
        searchBooksTask.execute();
    }

    public void loadData(ArrayList<Book> result) {

        Log.d("SearchActivity", result.toString());

        // carica i libri nella lista
        for(com.example.com.parsingData.parseType.Book bookResult : result) {

            Log.d("SearchActivity", "loading data...");

            resultListData.add(bookResult);
        }

        ((BookListAdapter)(resultList.getAdapter())).notifyDataSetChanged();
    }
}
