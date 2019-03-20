package com.example.com.literarium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import geoLocalization.GeoLocalizationActivity;
import geoLocalization.SendIpAddressTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // send my IP to the server
        SendIpAddressTask sendIpAddressTask = new SendIpAddressTask(this);
        sendIpAddressTask.execute();
    }

    public void startGeolocalization(View b) {

        Intent geoLocalizationIntent = new Intent(this, GeoLocalizationActivity.class);
        startActivity(geoLocalizationIntent);
    }
}
