package com.example.com.literarium;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.colorGenerator.ColorGenerator;
import com.example.com.parsingData.parseType.Shelf;
import com.example.com.parsingData.parseType.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.squareup.picasso.Picasso;

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
    private ImageView profilePic;

    private PieChart chart;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        sharedPreferences = Globals.getSharedPreferences(this);

        name = findViewById(R.id.user_name);
        username = findViewById(R.id.user_username);
        gender = findViewById(R.id.user_gender);
        age = findViewById(R.id.user_age);
        friendsCount = findViewById(R.id.user_friendscount);
        reviewsCount = findViewById(R.id.user_reviewscount);
        interests = findViewById(R.id.user_interests);
        about = findViewById(R.id.user_about);
        profilePic = findViewById(R.id.profilePic);

        chart = findViewById(R.id.user_chart);

        chart.setHoleRadius(25f);
        chart.setHoleColor(R.color.litGrey);


        GetUserInfoTask getUserInfoTask = new GetUserInfoTask(this,
                sharedPreferences.getString(getString(R.string.username_setting), ""));
        getUserInfoTask.execute();
    }

    public void loadUserData(User u){

        name.setText(u.getName());
        username.setText(u.getUsername());
        gender.setText(u.getGender());
        age.setText(String.valueOf(u.getAge()));
        friendsCount.setText(String.valueOf(u.getFriends_count()));
        reviewsCount.setText(String.valueOf(u.getReviews_count()));
        interests.setText(u.getInterests());
        about.setText(u.getAbout());
        Picasso.get().load(u.getImage_url()).into(profilePic);

        // setting the pie chart
        List <PieEntry> entries = new ArrayList<PieEntry>();

        int count = 0;

        Shelf [] shelves = u.getShelves();
        for(int i = 0; i < shelves.length; i++) {
            if (shelves[i].getBook_count() != 0){
                count++;
                PieEntry pe = new PieEntry(shelves[i].getBook_count(), shelves[i].getName());
                entries.add(pe);
            }
        }

        PieDataSet pds = new PieDataSet(entries, "Label");
        ColorGenerator cg = new ColorGenerator(.9, .9);

        String [] colorsStr = cg.getRandomColorStrings(count);
        List <Integer> colors = new ArrayList<>();

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

    public void logout(View v) {

        Log.d("CIAONE", "logout called");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.user_id_setting));
        editor.remove(getString(R.string.user_token_setting));
        editor.remove(getString(R.string.username_setting));
        editor.commit();

        updateLastAccessTimestamp();

        // redirect to the main
        Intent i = new Intent(UserShowActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void updateLastAccessTimestamp() {
        String timestamp = Globals.getTimestamp();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.last_access_setting), timestamp);
        editor.commit();
    }

    public void handleUserNotFound() {

        Toast.makeText(this, "user doesn't exist in GoodReads", Toast.LENGTH_LONG).show();
    }

}
