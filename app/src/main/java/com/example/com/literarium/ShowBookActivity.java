package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.geoLocalization.GeoLocalizationActivity;
import com.example.com.localDB.SaveBookTask;
import com.example.com.parsingData.enumType.BookType;
import com.example.com.parsingData.parseType.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowBookActivity extends Activity {

    private Context ctx;

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookRating;
    private TextView bookPublishDate;
    private TextView bookDescription;

    private ImageView bookCover;

    private ImageButton saveBookButton;

    /**
     * complete data of the book
     * this is necessary to make book-saving possible
     */
    private Book bookObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);

        ctx = this;

        Bundle data = getIntent().getExtras();

        saveBookButton = findViewById(R.id.saveBookButton);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookRating = findViewById(R.id.bookRating);
        bookPublishDate = findViewById(R.id.bookPublishDate);
        bookDescription = findViewById(R.id.bookDescription);
        bookDescription.setSelected(true);
        bookCover = findViewById(R.id.bookCover);

        bookObj = data.getParcelable(getString(R.string.book_data));

        loadBookData(bookObj);

        /*GetBookDataTask getBookDataTask = new GetBookDataTask(this, data.getInt("bookId"));
        getBookDataTask.execute();*/

        // go to the author activity when clicking on his name
        bookAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent showAuthor = new Intent(ctx, AuthorShowActivity.class);

                Bundle authorInfo = new Bundle();
                authorInfo.putParcelable(getString(R.string.author_info_data), bookObj.getAuthor());

                showAuthor.putExtras(authorInfo);

                startActivity(showAuthor);
            }
        });
    }

    public void saveBook(View v) {

        ArrayList<Book> toSaveBookList = new ArrayList<>();
        toSaveBookList.add(bookObj);

        SaveBookTask saveBookTask = new SaveBookTask(this, toSaveBookList, BookType.SAVED_BOOK);
        saveBookTask.execute();
    }

    public void loadBookData(com.example.com.parsingData.parseType.Book b) {

        bookObj = b;

        bookTitle.setText(b.getTitle());
        bookAuthor.setText(b.getAuthor().getName());
        
        if(!b.getPublicationYear().isEmpty())
            bookPublishDate.setText(String.valueOf(b.getPublicationYear()));
        else
            findViewById(R.id.bookPublishDateFather).setVisibility(View.GONE);

        if(!String.valueOf(b.getAverageRating()).isEmpty())
            bookRating.setText(String.valueOf(b.getAverageRating()));
        else
            findViewById(R.id.bookRatingFather).setVisibility(View.GONE);

        if(!b.getDescription().isEmpty()){
            bookDescription.setHint("");
            bookDescription.setEms(b.getDescription().length());
            bookDescription.setText(Html.fromHtml(b.getDescription()));
        }
        else
            findViewById(R.id.bookDescriptionFather).setVisibility(View.GONE);

        Picasso.get().load(b.getImageUrl()).into(bookCover);
    }

    public void handleBookSavingSuccess() {

        Toast.makeText(this, "book saved successfully!", Toast.LENGTH_SHORT).show();
    }

    public void handleDuplicateSavedBook() {

        Toast.makeText(this, "book already in your list", Toast.LENGTH_SHORT).show();
    }

    public void goToGeolocalization(View v) {

        // make bundle with the data of the book to be shared
        Bundle b = new Bundle();
        b.putInt("bookId", bookObj.getId());
        b.putString("bookTitle", bookObj.getTitle());
        b.putString("bookIsbn", bookObj.getIsbn());
        b.putString("bookImageUrl", bookObj.getImageUrl());
        b.putString("bookPubYear", bookObj.getPublicationYear());
        b.putString("bookPublisher", bookObj.getPublisher());
        b.putString("bookDescription", bookObj.getDescription());
        b.putString("bookNumPages", bookObj.getNumPages());
        b.putDouble("bookRating", bookObj.getAverageRating());
        b.putParcelable("bookAuthor", bookObj.getAuthor());

        Intent i = new Intent(this, GeoLocalizationActivity.class);
        i.putExtras(b);
        startActivity(i);
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
