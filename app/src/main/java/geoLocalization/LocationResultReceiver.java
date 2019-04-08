package geoLocalization;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

public class LocationResultReceiver extends ResultReceiver {

    private Context context;

    private LocationSender callingTask;

    // NOTE: you should pass an activity context rather than an application context
    // so pass 'this' instead of getApplicationContext()
    public LocationResultReceiver(LocationSender at, Handler handler) {

        super(handler);
        callingTask = at;
        context = null;
    }

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
                callingTask.fillUserData(new UserData(location, addressOutput));
            }
            else if(context != null) {

                // really...don't pass a Context
            }
        }
        catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
