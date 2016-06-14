package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Karskrin on 14/06/2016.
 */
public class Request {
    @Expose
    public Ambiance ambiance;
    @Expose
    public Integer user_id;
    @SerializedName("access_token")
    @Expose
    String token;
    @SerializedName("ambiance_id")
    @Expose
    String id;

    public Request(String t, String id, AmbianceModel ambianceModel) {
        this.token = t;
        this.id = id;
        this.ambiance = ambianceModel.ambiance;
        this.user_id = ambianceModel.user_id;
    }
}