package com.android.bathhack.models;

import com.google.firebase.firestore.GeoPoint;

public class UserModel {

    private GeoPoint geoPoint;


    public UserModel() {
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }
}
