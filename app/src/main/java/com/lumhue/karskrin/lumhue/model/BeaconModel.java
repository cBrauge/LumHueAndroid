package com.lumhue.karskrin.lumhue.model;


import android.graphics.Color;

public class BeaconModel {
    public String type;
    public String distance;
    public int color;

    public BeaconModel(String type, String distance) {
        this.type = type;
        this.distance = distance;
        if (type == "Bike")
            color = Color.YELLOW;
        else if (type == "Door")
            color = Color.CYAN;
    }
}
