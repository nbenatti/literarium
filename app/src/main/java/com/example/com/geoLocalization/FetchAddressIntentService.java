package com.example.com.geoLocalization;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.com.literarium.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * background service to perform reverse geocoding operation.
 */
public class FetchAddressIntentService extends IntentService {

    protected ResultReceiver resultReceiver;

    public FetchAddressIntentService() {
        super(null);
    }

    public FetchAddressIntentService(String name) {
        super(name);
    }

    private void deliverErrorToReceiver(int resultCode, String errorMessage) {

        Bundle b = new Bundle();
        b.putString(Constants.RESULT_DATA, errorMessage);
        resultReceiver.send(resultCode, b);
    }

    private void deliverResultToReceiver(int resultCode, LocationPackage content) {

        Bundle b = new Bundle();
        b.putString(Constants.RESULT_DATA, content.getStreetAddress());
        b.putParcelable(Constants.LOCATION_DATA, content.getLocation());
        resultReceiver.send(resultCode, b);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if(intent == null)
            return;

        String errorMessage = "";

        // fetch bundled data from the caller activity
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA);
        resultReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        Geocoder geocoder = new Geocoder(this, Locale.ITALIAN);

        // addresses list
        List<Address> addressList = null;

        try {
            // it does the job :)
            addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }
        catch (IOException e) {

            errorMessage = getString(R.string.error_network_problem);
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {

            errorMessage = getString(R.string.error_invalid_geo_coordinates);
            e.printStackTrace();
        }

        if(addressList == null || addressList.size() == 0) {
            if(errorMessage.isEmpty())
                errorMessage = getString(R.string.error_no_address_found);

            Log.d("rev_geocoding_debug", "errore: " + errorMessage);

            // deliver result
            deliverErrorToReceiver(Constants.FAILURE_RESULT, errorMessage);
        }
        else {

            // encode the address into a string
            Address fetchedAddress = addressList.get(0);
            //ArrayList<String> addrFragments = new ArrayList<>();

            Log.d("rev_geocoding_debug", "indirizzo: " + fetchedAddress.toString());

            //TODO: some addresses may be more than 1 line long...
            String encodedAddress = fetchedAddress.getAddressLine(0).trim();

            // packetize coordinates and address in a single object
            LocationPackage lp = new LocationPackage(location, encodedAddress);

            // deliver result
            deliverResultToReceiver(Constants.SUCCESS_RESULT, lp);
        }
    }
}
