package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Lumhuemodel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("reachable")
    @Expose
    private Integer reachable;
    @SerializedName("on")
    @Expose
    private Integer on;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The reachable
     */
    public Integer getReachable() {
        return reachable;
    }

    /**
     * @param reachable The reachable
     */
    public void setReachable(Integer reachable) {
        this.reachable = reachable;
    }

    /**
     * @return The on
     */
    public Integer getOn() {
        return on;
    }

    /**
     * @param on The on
     */
    public void setOn(Integer on) {
        this.on = on;
    }
}
