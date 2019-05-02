package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.dataAcquisition.Book;
import com.example.com.geoLocalization.GeoLocalizationActivity;
import com.example.com.localDB.SaveBookTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ShowBookActivity extends Activity {

    private TextView bookTitle;
    private TextView bookAuthor;
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

        Bundle data = getIntent().getExtras();

        saveBookButton = findViewById(R.id.saveBookButton);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookPublishDate = findViewById(R.id.bookPublishDate);
        bookDescription = findViewById(R.id.bookDescription);
        bookDescription.setSelected(true);
        bookCover = findViewById(R.id.bookCover);

        GetBookDataTask getBookDataTask = new GetBookDataTask(this, /*data.getInt("bookId")*/16034833);
        getBookDataTask.execute();
    }

    public void saveBook(View v) {

        /*Book b = new Book(50,
                        "Hatchet",
                        "0689840926",
                    "https://s.gr-assets.com/assets/nophoto/book/111x148-bcc042a9c91a29c1d680899eff700a03.png",
                2000,
                    "Atheneum Books for Young Readers: Richard Jackson Books",
                   "Brian is on his way to Canada to visit his estranged father when the pilot " +
                            "of his small prop plane suffers a heart attack. Brian is forced to crash-land the plane in a lake--and " +
                            "finds himself stranded in the remote Canadian wilderness with only his clothing and the " +
                            "hatchet his mother gave him as a present before his departure",
                "https://www.goodreads.com/book_link/follow/1",
                   208,
                     "Gary Paulsen");*/

        SaveBookTask saveBookTask = new SaveBookTask(this, bookObj);
        saveBookTask.execute();
    }

    public void loadBookData(Book b) {

        bookObj = b;

        bookTitle.setText(b.getTitle());
        bookAuthor.setText(b.getAuthor());
        bookPublishDate.setText(String.valueOf(b.getPublicationYear()));
        bookDescription.setHint("");
        bookDescription.setEms(b.getDescription().length());
        bookDescription.setText(Html.fromHtml(b.getDescription()));
        Picasso.get().load(b.getImageURL()).into(bookCover);

    }

    public void handleBookSavingSuccess() {

        Toast.makeText(this, "book saved successfully!", Toast.LENGTH_SHORT).show();
    }

    public void goToGeolocalization(View v) {

        // make bundle with the data of the book to be shared
        Bundle b = new Bundle();
        b.putInt("bookId", bookObj.getId());
        b.putString("bookTitle", bookObj.getTitle());
        b.putString("bookIsbn", bookObj.getIsbn());
        b.putString("bookImageUrl", bookObj.getImageURL());
        b.putInt("bookPubYear", bookObj.getPublicationYear());
        b.putString("bookPublisher", bookObj.getPublisher());
        b.putString("bookDescription", bookObj.getDescription());
        b.putString("bookAmazonBuyLink", bookObj.getAmazonBuyLink());
        b.putInt("bookNumPages", bookObj.getNumPages());
        b.putString("bookAuthor", bookObj.getAuthor());

        Intent i = new Intent(this, GeoLocalizationActivity.class);
        i.putExtras(b);
        startActivity(i);
    }
}
