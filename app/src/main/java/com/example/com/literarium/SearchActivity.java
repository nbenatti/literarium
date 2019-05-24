package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

    private Button loadMoreButton;

    private ProgressBar progressBar;

    /* == data == */
    private int searchResultsPageIndex;

    private int selectedRadioid;

    private String searchString;

    /**
     * previous last item of the list
     */
    private int prvLastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //getActionBar().hide();

        ctx = this;

        searchResultsPageIndex = 0;

        keyword = findViewById(R.id.search_bar);
        resultList = findViewById(R.id.resultList);

        /*loadMoreButton = findViewById(R.id.loadMoreButton);
        loadMoreButton.setVisibility(View.INVISIBLE);*/

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
                bookData.putString(getString(R.string.book_type), "searched");

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });

        resultList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lwId = absListView.getId();

                if(lwId == resultList.getId() && resultListData.size() > 0) {
                    int lastItem = firstVisibleItem + visibleItemCount;

                    if (lastItem == totalItemCount) {
                        Log.d("SearchActivity", "list reached end of scrolling");

                        if(lastItem != prvLastItem) {
                            loadMore();
                            prvLastItem = lastItem;
                        }
                    }
                }
            }
        });

        rg = findViewById(R.id.fieldselection);
    }


    public void performSearch(View v) {

        searchString = this.keyword.getText().toString();

        searchResultsPageIndex = 1;

        // delete all data from the list, if there is any
        if(resultListData.size() > 0) {
            resultListData.clear();
            bookListAdapter.notifyDataSetChanged();
        }

        // getting selected radio button
        selectedRadioid = rg.getCheckedRadioButtonId();
        selectedRb = findViewById(selectedRadioid);

        SearchBooksTask searchBooksTask = new SearchBooksTask(this, searchString, computeSearchFilters(selectedRadioid), searchResultsPageIndex);
        searchBooksTask.execute();
        startLoadingRing();
    }

    public void loadMore() {

        searchResultsPageIndex++;

        Log.d("SearchActivity", "page #" + searchResultsPageIndex);

        SearchBooksTask searchBooksTask = new SearchBooksTask(this, searchString, computeSearchFilters(selectedRadioid), searchResultsPageIndex);
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

        if(searchResultsPageIndex == 1)
            bookListAdapter.clear();

        resultListData.addAll(result);

        Log.d("SearchActivity", "DONE LOADING BOOKS");

        bookListAdapter.notifyDataSetChanged();
    }

    public void handleNoResults() {

        Toast.makeText(this, "no results :(", Toast.LENGTH_SHORT).show();
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

    public void deleteSearchString(View v) {

        keyword.setText("");
    }
}
