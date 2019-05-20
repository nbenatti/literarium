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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.com.parsingData.parseType.Book;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    private EditText keyword;
    private ArrayList<Book> resultListData;
    private ListView resultList;
    private BookListAdapter bookListAdapter;
    private Context ctx;

    private RadioGroup rg;
    private RadioButton selectedRb;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //getActionBar().hide();

        ctx = this;

        keyword = findViewById(R.id.search_bar);
        resultList = findViewById(R.id.resultList);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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

        rg = findViewById(R.id.fieldselection);

    }

    public void goToBookSHow(View v) {

        Intent i = new Intent(this, ShowBookActivity.class);
        startActivity(i);
    }

    public void performSearch(View v) {

        String keyword = this.keyword.getText().toString();

        // getting selected radio button
        int selectedRadioid = rg.getCheckedRadioButtonId();
        selectedRb = findViewById(selectedRadioid);

        SearchBooksTask searchBooksTask = new SearchBooksTask(this, keyword, computeSearchFilters(selectedRadioid));
        searchBooksTask.execute();

        startLoadingRing();
    }

    private String computeSearchFilters(int selectedRadioid) {

        if(selectedRadioid == R.id.all_rb)
            return "all";
        else if(selectedRadioid == R.id.author_rb)
            return "author";
        else
            return "title";
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

    public void startLoadingRing() {

        progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoadingRing() {

        progressBar.setVisibility(View.GONE);
    }

    public void goToSavedBooks(View v) {

        Intent i = new Intent(this, SavedBooksActivity.class);
        startActivity(i);
    }

    public void goToUserLayout(View v) {

        Intent i = new Intent(this, UserShowActivity.class);
        startActivity(i);
    }

}
