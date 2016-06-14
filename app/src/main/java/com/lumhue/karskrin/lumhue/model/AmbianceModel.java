package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Karskrin on 06/06/2016.
 */
public class AmbianceModel {
    @Expose(serialize = false)
    public Id _id;
    @Expose
    public Ambiance ambiance;
    @Expose
    public Integer user_id;
}
