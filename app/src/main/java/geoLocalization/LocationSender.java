package geoLocalization;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;

public class LocationSender extends AsyncTask {
    /**
     * reference to the activity to update the View
     */
    @SuppressLint("StaticFieldLeak")
    private GeoLocalizationActivity main;

    /**
     * server UDP port for nearby service
     */
    private final int PORT = 6000;
    private final int MINUTE = 1000*60;

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private LocationRequest lr;
    private LocationResultReceiver resultReceiver;

    private UserData userData;

    public LocationSender(GeoLocalizationActivity main) {
        this.main = main;
    }

    @Override
    protected void onPreExecute() {

        locationClient = LocationServices.getFusedLocationProviderClient(main);
        resultReceiver = new LocationResultReceiver(this, new Handler());
        // define the callback
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                if(locationResult == null)
                    return;

                Location loc = locationResult.getLocations().get(0);

                Log.d("DEBUG", "got location: " + loc);

                // start reverse geocoding service (auto-closes)
                startRevGeocodingIntentService(loc);
            }
        };
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Log.d("locationSender", "task started");

        DatagramSocket dsock = null;

        try {
            dsock = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] recBuf = new byte[1024];

        DatagramPacket dgram = new DatagramPacket(recBuf, recBuf.length);

        while(true) {

            Log.d("locationSender", "waiting for dgrams...");

            // === receive command ===
            try {
                // blocking call
                dsock.receive(dgram);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String recString = new String(Arrays.copyOfRange(recBuf, 0, dgram.getLength()));

            Log.d("locationSender", "received command: " + recString);

            SocketAddress clientAddress = dgram.getSocketAddress();

            Log.d("locationSender", "client address: " + clientAddress);

            // === get <address, GPS coordinates> and send them back to the server ===

            getRealTimeLocation();

            Log.d("locationSender", "final data: " + userData);

            byte[] buffer = userData.toString().getBytes();

            dgram = new DatagramPacket(buffer, buffer.length, dgram.getAddress(), PORT);

            try {
                dsock.send(dgram);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(Object o) {

        Log.d("locationSender", "task finished");
    }

    protected void startRevGeocodingIntentService(Location location) {
        Intent intent = new Intent(main, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, resultReceiver);
        intent.putExtra(Constants.LOCATION_DATA, location);
        main.startService(intent);
    }

    public void getRealTimeLocation() {

        // builder pattern
        lr = LocationRequest.create().
                setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).
                setInterval(MINUTE).
                setFastestInterval(10 * MINUTE);

        try {
            locationClient.requestLocationUpdates(this.lr, this.locationCallback, null);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets user's data back from the ResultReceiver
     */
    protected void fillUserData(UserData ud) {

        userData = ud;
    }
}
