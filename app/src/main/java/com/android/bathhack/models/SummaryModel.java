package com.android.bathhack.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SummaryModel implements Parcelable {
    private int hearts, coins, hazards;
    private double distance;
    private long time;

    public SummaryModel(int hearts, int coins, int hazards, double distance, long time) {
        this.hearts = hearts;
        this.coins = coins;
        this.hazards = hazards;
        this.distance = distance;
        this.time = time;
    }

    protected SummaryModel(Parcel in) {
        hearts = in.readInt();
        coins = in.readInt();
        hazards = in.readInt();
        distance = in.readInt();
        time = in.readLong();
    }

    public static final Creator<SummaryModel> CREATOR = new Creator<SummaryModel>() {
        @Override
        public SummaryModel createFromParcel(Parcel in) {
            return new SummaryModel(in);
        }

        @Override
        public SummaryModel[] newArray(int size) {
            return new SummaryModel[size];
        }
    };

    public int getHearts() {
        return hearts;
    }

    public int getCoins() {
        return coins;
    }

    public int getHazards() {
        return hazards;
    }

    public double getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hearts);
        parcel.writeInt(coins);
        parcel.writeInt(hazards);
        parcel.writeDouble(distance);
        parcel.writeLong(time);
    }
}
