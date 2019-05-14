package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.com.geoLocalization.UserListAdapter;
import com.example.com.localDB.Book;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText keyword;

    private ArrayList<Book> resultListData;

    private ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //getActionBar().hide();

        keyword = findViewById(R.id.search_bar);
        resultList = findViewById(R.id.result_list);

        BookListAdapter bookListAdapter = new BookListAdapter(this, R.layout.book_item, resultListData);

        resultList.setAdapter(bookListAdapter);
    }

    public void goToBookSHow(View v) {

        Intent i = new Intent(this, ShowBookActivity.class);
        startActivity(i);
    }

    public View performSearch(View v) {

        String keyword = this.keyword.getText().toString();

        SearchBooksTask searchBooksTask = new SearchBooksTask(this, keyword);
        searchBooksTask.execute();
    }

    private void loadData(ArrayList<Book> result) {

        // carica i libri nella lista
        for(Book bookResult : result) {

            resultListData.add(bookResult);
        }

        ((BookListAdapter)(resultList.getAdapter())).notifyDataSetChanged();
    }
}
