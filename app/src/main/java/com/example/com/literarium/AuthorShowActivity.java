package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.com.parsingData.parseType.Author;
import com.example.com.parsingData.parseType.AuthorInfo;
import com.squareup.picasso.Picasso;

public class AuthorShowActivity extends Activity {

    private Author authorObj;

    private TextView name;
    private TextView homeTown;
    private TextView fansCount;
    private TextView bornAt;
    private TextView diedAt;
    private TextView bookCount;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_layout);

        Bundle data = getIntent().getExtras();

        name = findViewById(R.id.authorName);
        homeTown = findViewById(R.id.homeTown);
        fansCount = findViewById(R.id.fansCount);
        bornAt = findViewById(R.id.bornAt);
        diedAt = findViewById(R.id.diedAt);
        bookCount = findViewById(R.id.workCount);
        image = findViewById(R.id.authorImage);

        AuthorInfo authorInfo = data.getParcelable(getString(R.string.author_info_data));

        GetAuthorDataTask getAuthorDataTask = new GetAuthorDataTask(this, authorInfo.getId());
        getAuthorDataTask.execute();
    }

    public void loadAuthorData(Author a) {

        name.setText(a.getInfo().getName());
        homeTown.setText(a.getHomeTown());
        fansCount.setText(String.valueOf(a.getFans_count()));
        bornAt.setText(a.getBorn_at());
        diedAt.setText(a.getDied_at());
        bookCount.setText(String.valueOf(a.getWorks_count()));
        Picasso.get().load(a.getImage_url()).into(image);
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
