package com.example.com.literarium;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.geoLocalization.Constants;
import com.example.com.geoLocalization.FetchAddressIntentService;
import com.example.com.geoLocalization.GeoLocalizationActivity;
import com.example.com.geoLocalization.LocationResultReceiver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends Activity {

    /**
     * activity context.
     */
    private Context ctx;

    /**
     * application settings.
     */
    SharedPreferences sharedPreferences;

    private final int MINUTE = 1000*60;
    private final int SECOND = MINUTE / 60;

    /**
     * client to interact with the GPS module of the phone
     * and fetch location data
     */
    private static FusedLocationProviderClient locationClient;

    /**
     * callback method te execute when the location arrives from the location client.
     */
    private LocationCallback locationCallback;

    /**
     * request that is sent to the location client.
     */
    private LocationRequest lr;

    /**
     * gets the final data and stores it on the central DB.
     */
    private LocationResultReceiver resultReceiver;

    private ScheduledExecutorService scheduledExecutorService;

    /* == GUI components == */
    private TextView welcomeMessage;

    private ListView newSharesList;

    private ArrayList<com.example.com.dataAcquisition.parseType.Book> newSharesListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().hide();

        ctx = this;

        // get preferences
        sharedPreferences = ctx.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        /*LinearLayout noConnectionBanner = findViewById(R.id.noConnectionBanner);
        noConnectionBanner.setVisibility(View.INVISIBLE);*/

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText(Globals.getInstance().getUserLocalData().getUserName()+"!");

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new LocationResultReceiver(this, new Handler());
        // define the callback
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                if(locationResult == null)
                    return;

                Location loc = locationResult.getLocations().get(0);

                Log.d("LocationSenderService", "got location: " + loc);

                // start reverse geocoding service (auto-closes)
                startRevGeocodingIntentService(loc);
            }
        };

        // start to periodically query the GPS
        getRealTimeLocation();

        // instantiate the list
        /*newSharesListData = new ArrayList<>();
        BookListAdapter bookListAdapter = new BookListAdapter(this, R.layout.book_item, newSharesListData);
        newSharesList.setAdapter(bookListAdapter);*/

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // save the last access timestamp in SharedPreferences
        String timestamp = Globals.getTimestamp();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.last_access_setting), timestamp);
        editor.commit();

        Log.d("MainActivity", "timestamp saved in preferences");
    }

    public static FusedLocationProviderClient getLocationClient() {

        return locationClient;
    }

    protected void startRevGeocodingIntentService(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA, location);
        startService(intent);
    }

    public void getRealTimeLocation() {

        // builder pattern
        lr = LocationRequest.create().
                setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).
                setInterval(MINUTE).
                setFastestInterval(10 * SECOND);

        try {
            locationClient.requestLocationUpdates(this.lr, this.locationCallback, null);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void goToSearchLayout(View v) {

        Intent i = new Intent(this, ShowBookActivity.class);
        startActivity(i);
    }
}
