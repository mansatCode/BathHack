package com.android.bathhack.models;

public class SummaryModel {
    private int hearts, coins, hazards, distance;
    private long time;

    public SummaryModel(int hearts, int coins, int hazards, int distance, long time) {
        this.hearts = hearts;
        this.coins = coins;
        this.hazards = hazards;
        this.distance = distance;
        this.time = time;
    }

    public int getHearts() {
        return hearts;
    }

    public int getCoins() {
        return coins;
    }

    public int getHazards() {
        return hazards;
    }

    public int getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }

}
