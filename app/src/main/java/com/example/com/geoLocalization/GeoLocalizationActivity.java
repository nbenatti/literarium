package com.example.com.geoLocalization;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.com.bookSharing.ShareBookTask;
import com.example.com.dataAcquisition.Book;
import com.example.com.literarium.IListableActivity;
import com.example.com.literarium.R;
import com.example.com.localDB.User;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * shows a map with the last location of the users.<br/>
 * the user can then decide wether to share a book with one user.
 */
public class GeoLocalizationActivity extends Activity implements IListableActivity {

    private MapView map;
    private MapboxMap mapBox;

    /**
     * list of users in the GUI
     */
    private ListView usersList;

    /**
     * actual data of the list
     */
    private ArrayList<UserData> list;

    /**
     * book the user has decided to share.
     */
    private Book toShare;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("GeoLocalizationActivity", "activity created");

        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());

        Context ctx = this;

        // inflate the wiew
        setContentView(R.layout.geolocalization_activity);

        // get the book data
        Bundle bookData = getIntent().getExtras();
        /*if(bookData != null) {
            toShare = new Book(bookData.getInt("bookId"),
                    bookData.getString("bookTitle"),
                    bookData.getString("bookIsbn"),
                    bookData.getString("bookImageUrl"),
                    bookData.getInt("bookPubYear"),
                    bookData.getString("bookPublisher"),
                    bookData.getString("bookDescription"),
                    bookData.getString("bookAmazonBuyLink"),
                    bookData.getInt("bookNumPages"),
                    bookData.getString("bookAuthor"));
        }*/

        toShare = new Book(50,
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
                "Gary Paulsen");

        // get UI components
        usersList = findViewById(R.id.usersList);
        map = (com.mapquest.mapping.maps.MapView)findViewById(R.id.mapQuest);
        map.onCreate(savedInstanceState);
        mapBox = null;
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                Log.d("GeoLocalizationActivity", "map initialized");
                mapBox = mapboxMap;
                map.setStreetMode();
            }
        });

        // set file path
        //SettingsReader.setConfigFilePath(getFilesDir().getAbsolutePath());

        // send IP address to the phone
        // TODO: this must be done in the mainActivity of the app
        /*SendIpAddressTask sendIpAddressTask = new SendIpAddressTask(this);
        sendIpAddressTask.execute();*/


        // send request to server, to retrieve location of other phones
        RetrieveUsersLocationTask rult = new RetrieveUsersLocationTask(this);
        rult.execute();


        // ask for permissions
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1000);

            return;
        }

        // create the list
        list = new ArrayList<>();
        UserListAdapter listAdapter = new UserListAdapter(
                this,
                R.layout.list_item,
                list);
        usersList.setAdapter(listAdapter);
        // when item is pressed, zoom the map on that position
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Location itemLocation = list.get(i).getLocation();

                mapBox.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(itemLocation), 13));
            }
        });
        // when item gets pressed for a long time, share the book
        usersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(), "long press!", Toast.LENGTH_SHORT).show();

                Log.d("GeoLocalizationActivity", "selected user: " + list.get(i).toString());

                // do something only if the user has a book to share (should be 100% of the times)
                if(toShare != null) {
                    UserData selectedUser = list.get(i);

                    ShareBookTask shareBookTask = new ShareBookTask(ctx);
                    shareBookTask.execute(selectedUser.getId(), toShare.getId());
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
        Log.d("GeoLocalizationActivity", "activity resumed, map" + ((mapBox==null)?" null":" not null"));

        //while(mapBox == null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
        Log.d("GeoLocalizationActivity", "activity paused, map" + ((mapBox==null)?" null":" not null"));
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        map.onStop();
        Log.d("GeoLocalizationActivity", "activity stopped, map" + ((mapBox==null)?" null":" not null"));
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        Log.d("GeoLocalizationActivity", "activity destroyed, map" + ((mapBox==null)?" null":" not null"));
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if(grantResults.length > 0) {
                    int i = 0;
                    for(Integer res : grantResults) {
                        Log.d("PERMISSION", permissions[i]+": " + ((res == PermissionChecker.PERMISSION_GRANTED) ? "granted" : "denied"));
                        i++;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * populates the list after having received the users location.
     *
     */
    public void populateList(List<? extends Cloneable> dataList) {

        while (mapBox == null);

        if(dataList.size() == 0)
            return;

        List<UserData> userDataList = null;

        if(dataList.get(0) instanceof UserData)
            userDataList = (List<UserData>)dataList;

        Log.d("GeoLocalizationActivity", "populating list");
        for(UserData ud : userDataList) {
            list.add(ud);
        }

        Log.d("GeoLocalizationActivity", "data in the list" + list.toString());

        ((UserListAdapter)(usersList.getAdapter())).notifyDataSetChanged();
    }

    /**
     * displays the users location on the map
     */
    public void populateMapWithMarkers() {

        // don't do anything whether the user list is empty (don't call this method too early)
        if(this.list == null || this.list.size() == 0)
            return;

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mapBox == null) {
            Log.d("GeoLocalizationActivity", "map is null");
        }

        for(UserData ud : list) {

            MarkerOptions newMarker = new MarkerOptions();
            newMarker.position(new LatLng(ud.getLocation()));
            newMarker.title(ud.getAddress());
            mapBox.addMarker(newMarker);
        }
    }

    public void shareBook(View v) {

        // connect to the webservice and update the DB
        ShareBookTask shareBookTask = new ShareBookTask(this);
        shareBookTask.execute();
    }

    // === handle operation results
    public void handleBookSharingSuccess() {

        Toast.makeText(this, "book shared!", Toast.LENGTH_SHORT).show();
    }
}
