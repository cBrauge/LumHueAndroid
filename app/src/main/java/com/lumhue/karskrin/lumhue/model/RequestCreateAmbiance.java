package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCreateAmbiance {
    @Expose
    public Ambiance ambiance;
    @SerializedName("access_token")
    @Expose
    String token;

    public RequestCreateAmbiance(String t, AmbianceModel ambianceModel) {
        this.token = t;
        this.ambiance = ambianceModel.ambiance;
    }
}