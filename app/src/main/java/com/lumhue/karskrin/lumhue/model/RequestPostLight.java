package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPostLight {
    @SerializedName("access_token")
    @Expose
    String token;
    @SerializedName("color")
    @Expose
    public String rgb;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("on")
    @Expose
    public String on;

    public RequestPostLight(String t, String rgb, Integer id, String on) {
        this.token = t;
        this.rgb = rgb;
        this.id = id;
        this.on = on;
    }
}