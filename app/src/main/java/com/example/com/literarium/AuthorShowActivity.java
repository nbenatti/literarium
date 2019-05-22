package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    private TextView biography;
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
        biography = findViewById(R.id.about);
        image = findViewById(R.id.authorImage);

        AuthorInfo authorInfo = data.getParcelable(getString(R.string.author_info_data));

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
