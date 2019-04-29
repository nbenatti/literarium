package com.example.com.literarium;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowBookActivity extends Activity {

    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookPublishDate;

    private ImageButton saveBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);

        Bundle data = getIntent().getExtras();

        saveBookButton = findViewById(R.id.saveBookButton);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookPublishDate = findViewById(R.id.bookPublishDate);

        GetBookDataTask getBookDataTask = new GetBookDataTask(this);
        getBookDataTask.execute();
    }

    private void saveBook(View v) {

        //SaveBookTask saveBookTask = new SaveBookTask(this, );
    }

    public void loadBookData(Book b) {

        bookTitle.setText(b.getTitle());
        bookAuthor.setText(b.getAuthor());
        bookPublishDate.setText(b.getPublicationYear());
    }
}
