package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText keyword;

    private ArrayList<Book> resultListData;

    private ListView resultList;

    private BookListAdapter bookListAdapter;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //getActionBar().hide();

        ctx = this;

        keyword = findViewById(R.id.search_bar);
        resultList = findViewById(R.id.resultList);

        resultListData = new ArrayList<>();
        bookListAdapter = new BookListAdapter(this, R.layout.book_item, resultListData);
        resultList.setAdapter(bookListAdapter);

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the book
                Intent showBook = new Intent(ctx, ShowBookActivity.class);

                Bundle bookData = new Bundle();
                bookData.putParcelable(getString(R.string.book_data), resultListData.get(i));

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });
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
        /*for(Book bookResult : result) {

            Log.d("SearchActivity", "loading data...");

            resultListData.add(bookResult);
        }*/

        bookListAdapter.clear();

        resultListData.addAll(result);

        Log.d("SearchActivity", "DONE LOADING BOOKS");

        bookListAdapter.notifyDataSetChanged();
    }
}
