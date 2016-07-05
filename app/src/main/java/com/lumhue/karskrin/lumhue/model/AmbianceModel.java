package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

public class AmbianceModel {
    @Expose(serialize = false)
    public Id _id;
    @Expose
    public Ambiance ambiance;
    @Expose
    public Integer user_id;

    public AmbianceModel() {
        this._id = new Id();
        this.ambiance = new Ambiance();
    }
}
