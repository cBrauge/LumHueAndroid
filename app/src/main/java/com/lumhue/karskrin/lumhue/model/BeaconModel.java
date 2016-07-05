package com.lumhue.karskrin.lumhue.model;


import android.graphics.Color;

public class BeaconModel {
    public String type;
    public String distance;
    public int color;

    public BeaconModel(String type, String distance) {
        this.type = type;
        this.distance = distance;
        if (type == "Bike" || type == "Bag")
            color = Color.YELLOW;
        else if (type == "Door" || type == "Bed")
            color = Color.CYAN;
        else if (type == "Fridge" || type == "Chair")
            color = Color.parseColor("#4D00BF");
        else if (type == "Car" || type == "Generic")
            color = Color.parseColor("#38C7B4");
        else if (type == "Dog" || type == "Shoe")
            color = Color.parseColor("#FF69B4");
    }
}
