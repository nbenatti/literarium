package geoLocalization;

import android.Manifest;
import android.app.Activity;
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

import com.example.com.literarium.IListableActivity;
import com.example.com.literarium.R;
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

    //private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("GeoLocalizationActivity", "activity created");

        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());

        // inflate the wiew
        setContentView(R.layout.geolocalization_activity);

        // get UI components
        usersList = findViewById(R.id.usersList);
        map = (com.mapquest.mapping.maps.MapView)findViewById(R.id.mapQuest);
        map.onCreate(savedInstanceState);
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
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Location itemLocation = list.get(i).getLocation();

                mapBox.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(itemLocation), 13));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
        Log.d("GeoLocalizationActivity", "activity resumed, map" + ((mapBox==null)?" null":" not null"));
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

        if(mapBox == null)
            Log.d("GeoLocalizationActivity", "map is null");

        for(UserData ud : list) {

            MarkerOptions newMarker = new MarkerOptions();
            newMarker.position(new LatLng(ud.getLocation()));
            newMarker.title(ud.getAddress());
            mapBox.addMarker(newMarker);
        }
    }

    // == handle exceptions ==
    protected void handleSocketTimeout(SocketTimeoutException e) {

        Toast.makeText(this, "server not responding...", Toast.LENGTH_LONG).show();
    }
}
