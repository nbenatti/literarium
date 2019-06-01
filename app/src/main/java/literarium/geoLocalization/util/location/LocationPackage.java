package com.example.com.geoLocalization;

import android.location.Location;

public class LocationPackage {

    private Location location;

    private String streetAddress;

    public LocationPackage(Location location, String streetAddress) {
        this.location = location;
        this.streetAddress = streetAddress;
    }

    public Location getLocation() {
        return location;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    @Override
    public String toString() {
        return "LocationPackage{" + "location=" + location + ", streetAddress='" + streetAddress + '\'' + '}';
    }
}
