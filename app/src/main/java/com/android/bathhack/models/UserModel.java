package com.android.bathhack.models;

import com.google.firebase.firestore.GeoPoint;

public class UserModel {

    private double longitude;
    private double latitude;

    public UserModel() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
