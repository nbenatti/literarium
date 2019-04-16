package geoLocalization;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.example.com.literarium.XMLUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
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

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private LocationRequest lr;
    private LocationResultReceiver resultReceiver;

    /**
     * output data of the process.
     */
    private UserData userData;

    public LocationSenderService() {

        super("ListenForLocationRequests");

        Log.d("ListenForLocationReq", "service created");

        /*locationClient = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new LocationResultReceiver(this, new Handler());
        // define the callback
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {

                if(locationResult == null)
                    return;

                Location loc = locationResult.getLocations().get(0);

                Log.d("ListenForLocationReq", "got location: " + loc);

                // start reverse geocoding service (auto-closes)
                startRevGeocodingIntentService(loc);
            }
        };*/
    }

    public LocationSenderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("ListenForLocationReq", "service started");

        //getRealTimeLocation();


        // connect to the webservice and store the location on the DB
        URL url = null;
        InputStream webServiceStream = null;
        Document response = null;

        try {

            String address = "Via Roma, Viadana, MN";

            double fakeLat = Math.random() * 90;
            double fakeLong = Math.random() * 90;

            url = new URL(WEBSERVICE_URL + "?userid=2&" +
                    "latitudine="+String.valueOf(fakeLat)+"&" +
                    "longitudine="+String.valueOf(fakeLong)+"&" +
                    "indirizzo="+ URLEncoder.encode(address, java.nio.charset.StandardCharsets.UTF_8.toString()));

            webServiceStream = url.openStream();

            response = XMLUtils.getNewDocFromStream(webServiceStream);
            Log.d("ListenForLocationReq", "webservice response: \n" + XMLUtils.docToString(response));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // send the data to the server
        //Log.d("ListenForLocationReq", "SERVICE OUTPUT: " + userData.toString());

        /*DatagramSocket dsock = null;

        try {
            dsock = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] recBuf = new byte[1024];

        DatagramPacket dgram = new DatagramPacket(recBuf, recBuf.length);

        boolean stop = false;

        while(!stop) {

            Log.d("ListenForLocationReq", "waiting for dgrams...");

            // === receive command ===
            try {
                // blocking call
                dsock.receive(dgram);

            } catch (IOException e) {
                e.printStackTrace();
            }

            String recString = new String(Arrays.copyOfRange(recBuf, 0, dgram.getLength()));

            Log.d("ListenForLocationReq", "received command: " + recString);

            SocketAddress clientAddress = dgram.getSocketAddress();

            Log.d("ListenForLocationReq", "client address: " + clientAddress);

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
        }*/


    }

    @Override
    public void onDestroy() {
        Log.d("ListenForLocationReq", "service destroyed");
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
                setFastestInterval(10 * MINUTE);

        try {
            if(locationClient == null)
                Log.d("ListenForLocationReq", "OCIO DE PT.2");
            locationClient.requestLocationUpdates(this.lr, this.locationCallback, null);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets user data back from the ResultReceiver
     */
    protected void fillUserData(UserData ud) {

        userData = ud;
    }
}
