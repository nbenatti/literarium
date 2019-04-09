package com.example.com.literarium;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import geoLocalization.GeoLocalizationActivity;
import geoLocalization.LocationSenderService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // send my IP to the server
        /*SendIpAddressTask sendIpAddressTask = new SendIpAddressTask(this);
        sendIpAddressTask.execute();*/

        scheduleService(10, LocationSenderService.class);
    }

    public void startGeolocalization(View b) {

        Intent geoLocalizationIntent = new Intent(this, GeoLocalizationActivity.class);
        startActivity(geoLocalizationIntent);
    }

    /**
     * starts the service scheduling it
     * to start every N minutes.
     */
    private void scheduleService(int minutes, Class serviceClass) {

        Intent serviceIntent = new Intent(this, serviceClass);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, minutes);

        // start the scheduled service
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                minutes*1000,
                pendingIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
