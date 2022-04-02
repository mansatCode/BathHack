package com.android.bathhack.models;

import com.google.firebase.firestore.GeoPoint;

public class UserLocation {

    private GeoPoint geoPoint;

    public UserLocation(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public UserLocation() {
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
