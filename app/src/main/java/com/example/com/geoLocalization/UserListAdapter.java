package com.example.com.geoLocalization;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.com.literarium.R;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserData> {

    private static final String TAG = UserListAdapter.class.getSimpleName();

    private int layoutId;
    private Context ctx;

    public UserListAdapter(Context context, int resource, List<UserData> objects) {
        super(context, resource, objects);

        layoutId = resource;
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        // inflate the list row layout if needed
        if(v == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(ctx);
            v = layoutInflater.inflate(layoutId, null);
        }

        // get the user data
        UserData usrData = getItem(position);

        if(usrData != null) {

            // get layout widgets
            TextView userName = v.findViewById(R.id.userName);
            TextView userAddress = v.findViewById(R.id.userAddress);

            // fill the layout with data
            if(userName != null)
                userName.setText(usrData.getName());
            if(userAddress != null)
                userAddress.setText(/*printGeoCoordinates(usrData.getLocation())*/usrData.getAddress());
        }

        return v;
    }

    private String printGeoCoordinates(Location l) {

        double lat = l.getLatitude(), lon = l.getLongitude();

        String formattedLatitude = lat + ((lat >= 0) ? "° N" : "° S");
        String formattedLongitude = lon + ((lon >= 0) ? "° E" : "° W" );

        return formattedLatitude + ", " + formattedLongitude;
    }
}
