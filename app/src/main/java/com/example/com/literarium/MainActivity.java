package com.example.com.literarium;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.geoLocalization.Constants;
import com.example.com.geoLocalization.FetchAddressIntentService;
import com.example.com.geoLocalization.LocationResultReceiver;
import com.example.com.localDB.BookDAO;
import com.example.com.localDB.LocalDatabase;
import com.example.com.localDB.ModifyBookTask;
import com.example.com.parsingData.parseType.Book;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int MINUTE = 1000*60;
    private final int SECOND = MINUTE / 60;

    /**
     * activity context.
     */
    private Context ctx;

    /**
     * reference to the local DB
     */
    private LocalDatabase dbRef;
    private BookDAO bookDao;

    /**
     * application settings.
     */
    private SharedPreferences sharedPreferences;

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

    private ArrayList<Book> newSharesListData;

    private BookListAdapter bookListAdapter;

    private ImageButton clearAll;

    private LinearLayout noConnectionBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        // get preferences
        sharedPreferences = ctx.getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        noConnectionBanner = findViewById(R.id.noConnectionBanner);
        noConnectionBanner.setVisibility(View.INVISIBLE);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText(sharedPreferences.getString(getString(R.string.username_setting), ""));

        clearAll = findViewById(R.id.clearAll);
        clearAll.setVisibility(View.INVISIBLE);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new LocationResultReceiver(this, new Handler());
        // define the callback
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                if(locationResult == null)
                    return;

                Location loc = locationResult.getLocations().get(0);

                Log.d(TAG, "got location: " + loc);

                // start reverse geocoding service (auto-closes)
                startRevGeocodingIntentService(loc);
            }
        };

        // start to periodically query the GPS
        getRealTimeLocation();

        // instantiate the list
        newSharesList = findViewById(R.id.newShares);
        newSharesListData = new ArrayList<>();
        bookListAdapter = new BookListAdapter(this, R.layout.book_item, newSharesListData);
        newSharesList.setAdapter(bookListAdapter);

        newSharesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show the book
                Intent showBook = new Intent(ctx, ShowBookActivity.class);

                Bundle bookData = new Bundle();
                bookData.putParcelable(getString(R.string.book_data), newSharesListData.get(i));
                bookData.putString(getString(R.string.book_type), "saved");

                showBook.putExtras(bookData);

                startActivity(showBook);
            }
        });

        // fetch data
        FetchNewSharesTask fetchNewSharesTask = new FetchNewSharesTask(this);
        fetchNewSharesTask.execute();
    }

    public void populate(ArrayList<Book> books) {
        if(books.size() > 0)
            clearAll.setVisibility(View.VISIBLE);

        for(Book runner : books)
            Log.d(TAG, runner.toString());

        newSharesListData.addAll(books);
        bookListAdapter.notifyDataSetChanged();
    }

    /**
     * clears all new shares
     * @param v the current view.
     */
    public void clearAll(View v) {

        newSharesListData.clear();
        bookListAdapter.notifyDataSetChanged();
        // set the button invisible again
        clearAll.setVisibility(View.INVISIBLE);

        // modify the seen status of the books
        ModifyBookTask modifyBookTask = new ModifyBookTask(ctx, newSharesListData, true, true);
        modifyBookTask.execute();
    }

    public void handleNoShares() {

        LinearLayout noConnectionBanner = findViewById(R.id.noConnectionBanner);
        noConnectionBanner.setVisibility(View.VISIBLE);
    }

    public void spawnNoConnBanner() {
        noConnectionBanner.setVisibility(View.VISIBLE);
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

        Log.d(TAG, "timestamp saved in preferences");
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
