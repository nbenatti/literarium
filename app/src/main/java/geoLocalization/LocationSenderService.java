package geoLocalization;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.com.literarium.HttpRequest;
import com.example.com.literarium.MainActivity;
import com.example.com.literarium.RequestManager;
import com.example.com.literarium.RequestType;
import com.example.com.literarium.XMLUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.transform.TransformerException;

public class LocationSenderService extends IntentService {

    /**
     * server UDP port for nearby service
     */
    //private final int PORT = 6000;
    private final String WEBSERVICE_URL = "http://192.168.1.7/literarium_api/insert_geo_data.php";
    private final int MINUTE = 1000*60;
    private final int SECOND = MINUTE / 60;

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private LocationRequest lr;
    private LocationResultReceiver resultReceiver;

    /**
     * output data of the <b>reverse geocoding</b> process.
     */
    private LocationPackage locationPackage;

    public LocationSenderService() {

        super("ListenForLocationRequests");

        Log.d("LocationSenderService", "service created");
    }

    public LocationSenderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("LocationSenderService", "service started");

        // fetch data coming from the location module
        locationPackage = new LocationPackage(
                intent.getParcelableExtra(Constants.LOCATION_DATA),
                intent.getStringExtra(Constants.RESULT_DATA)
        );

        // connect to the webservice and store the location on the DB
        URL url = null;
        InputStream webServiceStream = null;
        Document response = null;

        try {

            /*String address = "Via Roma, Viadana, MN";

            double fakeLat = Math.random() * 90;
            double fakeLong = Math.random() * 90;*/

            String requestUrl = null;
            try {
                requestUrl = RequestManager.formatRequest(
                        RequestType.LOG_POSITION,
                        2,
                        locationPackage.getLocation().getLatitude(),
                        locationPackage.getLocation().getLongitude(),
                        locationPackage.getStreetAddress());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("LocationSenderService", requestUrl);

            HttpRequest request = new HttpRequest(requestUrl, HttpRequest.HttpRequestMethod.GET);
            request.send();

            /*url = new URL(WEBSERVICE_URL + "?userid=2&" +
                    "latitudine="+String.valueOf(locationPackage.getLocation().getLatitude())+"&" +
                    "longitudine="+String.valueOf(locationPackage.getLocation().getLongitude())+"&" +
                    "indirizzo="+ URLEncoder.encode(locationPackage.getStreetAddress(), java.nio.charset.StandardCharsets.UTF_8.toString()));

            webServiceStream = url.openStream();

            response = XMLUtils.getNewDocFromStream(webServiceStream);*/
            response = request.getResult();
            Log.d("ListenForLocationReq", "webservice response: \n" + XMLUtils.docToString(response));

        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // send the data to the server
        //Log.d("ListenForLocationReq", "SERVICE OUTPUT: " + userData.toString());
    }

    @Override
    public void onDestroy() {
        Log.d("LocationSenderService", "service destroyed");
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

    /**
     * gets user data back from the ResultReceiver
     */
    protected void fillUserData(LocationPackage ud) {

        locationPackage = ud;
    }
}
