package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.parsingData.parseType.Author;
import com.example.com.parsingData.parseType.AuthorInfo;
import com.example.com.parsingData.parseType.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuthorShowActivity extends Activity {

    private static final String TAG = AuthorShowActivity.class.getSimpleName();

    private Context ctx;
    private Author authorObj;

    private TextView name;
    private TextView homeTown;
    private TextView fansCount;
    private TextView bornAt;
    private TextView diedAt;
    private TextView bookCount;
    private TextView biography;
    private ImageView image;

    private ArrayList<Book> bookListData;
    private ListView bookList;
    private BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_layout);

        ctx = this;

        Bundle data = getIntent().getExtras();

        name = findViewById(R.id.authorName);
        homeTown = findViewById(R.id.homeTown);
        fansCount = findViewById(R.id.fansCount);
        bornAt = findViewById(R.id.bornAt);
        diedAt = findViewById(R.id.diedAt);
        bookCount = findViewById(R.id.workCount);
        biography = findViewById(R.id.about);
        image = findViewById(R.id.authorImage);

        AuthorInfo authorInfo = data.getParcelable(getString(R.string.author_info_data));

        bookList = findViewById(R.id.authorBooks);
        bookListData = new ArrayList<>();
        bookListAdapter = new BookListAdapter(this, R.layout.book_item, bookListData);
        bookList.setAdapter(bookListAdapter);

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the book
                Intent showBook = new Intent(ctx, ShowBookActivity.class);

                Bundle bookData = new Bundle();
                bookData.putParcelable(getString(R.string.book_data), bookListData.get(i));
                bookData.putString(getString(R.string.book_type), "searched");

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });

        GetAuthorDataTask getAuthorDataTask = new GetAuthorDataTask(this, authorInfo.getId());
        getAuthorDataTask.execute();
    }

    public void loadAuthorData(Author a) {

        name.setText(a.getInfo().getName());

        if(!String.valueOf(a.getHomeTown()).isEmpty())
            homeTown.setText(a.getHomeTown());
        else
            findViewById(R.id.homeTown).setVisibility(View.GONE);

        if(!String.valueOf(a.getFans_count()).isEmpty())
            fansCount.setText(String.valueOf(a.getFans_count()));
        else
            findViewById(R.id.fansCountFather).setVisibility(View.GONE);

        if(!a.getBorn_at().isEmpty())
            bornAt.setText(a.getBorn_at());
        else
            findViewById(R.id.bornAtFather).setVisibility(View.GONE);

        if(!a.getDied_at().isEmpty())
            diedAt.setText(a.getDied_at());
        else
            findViewById(R.id.diedAtFather).setVisibility(View.GONE);

        if(!String.valueOf(a.getWorks_count()).isEmpty())
            bookCount.setText(String.valueOf(a.getWorks_count()));
        else
            findViewById(R.id.workCountFather).setVisibility(View.GONE);

        if(!a.getAbout().isEmpty()) {
            biography.setHint("");
            biography.setEms(a.getAbout().length());
            biography.setText(Html.fromHtml(a.getAbout()));
        }
        else
            findViewById(R.id.aboutFather).setVisibility(View.GONE);

        Picasso.get().load(a.getImage_url()).into(image);

        // load books
        populateBookList(new ArrayList<>(Arrays.asList(a.getBooks())));
    }

    private void populateBookList(List<Book> bookList) {

        Log.d(TAG, bookList.toString());

        bookListData.addAll(bookList);

        bookListAdapter.notifyDataSetChanged();
    }

    public void goToSearchLayout(View v) {

        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
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
