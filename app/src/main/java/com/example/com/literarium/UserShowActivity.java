package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.com.colorGenerator.ColorGenerator;
import com.example.com.parsingData.XmlDataParser;
import com.example.com.parsingData.parseType.Shelf;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class UserShowActivity extends Activity {

    private TextView name;
    private TextView username;
    private TextView gender;
    private TextView age;
    private TextView friendsCount;
    private TextView reviewsCount;
    private TextView interests;
    private TextView about;

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        name = findViewById(R.id.user_name);
        username = findViewById(R.id.user_username);
        gender = findViewById(R.id.user_gender);
        age = findViewById(R.id.user_age);
        friendsCount = findViewById(R.id.user_friendscount);
        reviewsCount = findViewById(R.id.user_reviewscount);
        interests = findViewById(R.id.user_interests);
        about = findViewById(R.id.user_about);

        chart = findViewById(R.id.user_chart);

        //XmlDataParser xdp = new XmlDataParser();

    }

    public void loadUserData(com.example.com.parsingData.parseType.User u){

        name.setText(u.getName());
        username.setText(u.getUsername());
        gender.setText(u.getGender());
        age.setText(u.getAge());
        friendsCount.setText(u.getFriends_count());
        reviewsCount.setText(u.getReviews_count());
        interests.setText(u.getInterests());
        about.setText(u.getAbout());

        // setting the pie chart
        List <PieEntry> entries = new ArrayList<PieEntry>();

        Shelf [] shelves = u.getShelves();
        for(int i = 0; i < shelves.length; i++){
            PieEntry pe = new PieEntry(shelves[i].getBook_count(), shelves[i].getName());
            entries.add(pe);
        }

        PieDataSet pds = new PieDataSet(entries, "Label");
        ColorGenerator cg = new ColorGenerator(.99, .99);

        String [] colorsStr = cg.getRandomColorStrings(5);
        List <Integer> colors = new ArrayList<Integer>();

        for(String cs : colorsStr)
            colors.add(Color.parseColor(cs));

        pds.setColors(colors);

        PieData pd = new PieData(pds);
        PieChart chart = (PieChart) findViewById(R.id.user_chart);
        chart.setData(pd);
        chart.setCenterText("Book statistics");
        chart.invalidate();

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
