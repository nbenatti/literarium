package com.example.com.geoLocalization;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class LocationResultReceiver extends ResultReceiver {

    private Context context;

    private LocationSenderService callingTask;

    // NOTE: you should pass an activity context rather than an application context
    // so pass 'this' instead of getApplicationContext()
    public LocationResultReceiver(LocationSenderService at, Handler handler) {

        super(handler);
        callingTask = at;
        context = null;
    }

    // it is provided but not
    public LocationResultReceiver(Context ctx, Handler handler) {

        super(handler);
        context = ctx;
        callingTask = null;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (resultData == null) {
            return;
        }

        if (resultCode == Constants.SUCCESS_RESULT) {
            Log.d("REV_GEOCODING_STATUS", "reverse geocoding succesful");
        }
        else {

        }

        // Display the address string
        // or an error message sent from the intent service.
        String addressOutput = resultData.getString(Constants.RESULT_DATA);
        if (addressOutput == null) {
            addressOutput = "";
        }
        Location location = resultData.getParcelable(Constants.LOCATION_DATA);

        Log.d("REV_GEOCODING_STATUS", "reverse geocoding output is: " + addressOutput);

        // populate users list
        try {

            /*if(context instanceof Activity) {

                Activity callerAct = (Activity) context;

                if(IListableActivity.class.isAssignableFrom(callerAct.getClass())) {

                    IListableActivity concreteAct = (IListableActivity)callerAct;
                    //concreteAct.populateList(new UserData(location, addressOutput));
                }
            }
            else {
                Log.d("DEBUG", "ERROR: the passed context is not a listableActivity, or is not possible to cast");
                return;
            }*/

            if(callingTask != null) {
                // pass the data back to the calling task
                //callingTask.fillUserData(new LocationPackage(location, addressOutput));
            }
            else if(context != null) {

                // start the intent service which stores the data on the DB
                Intent locationSender = new Intent(context, LocationSenderService.class);
                Bundle locationData = new Bundle();
                locationData.putString(Constants.RESULT_DATA, addressOutput);
                locationData.putParcelable(Constants.LOCATION_DATA, location);
                locationSender.putExtras(locationData);
                context.startService(locationSender);
            }
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
