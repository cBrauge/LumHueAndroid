package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Karskrin on 06/06/2016.
 */
public class Lightscolor {
    @Expose
    public Integer id;
    @Expose
    public String color;
    @Expose
    public Boolean on = true;
    @Expose
    public String rgbhex;

    public Lightscolor(Integer id, String color, Boolean on, String rgbhex) {
        this.id = id;
        this.color = color;
        this.on = on;
        this.rgbhex = rgbhex;
    }
}
