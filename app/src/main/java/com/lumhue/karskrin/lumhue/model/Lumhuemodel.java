package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Lumhuemodel {

    @SerializedName("bridgeIsOnline")
    @Expose
    private Boolean bridgeIsOnline;
    @SerializedName("lights")
    @Expose
    private Map<Integer, Light> lights;

    public Boolean getBridgeIsOnline() {
        return bridgeIsOnline;
    }

    public void setBridgeIsOnline(Boolean bridgeIsOnline) {
        this.bridgeIsOnline = bridgeIsOnline;
    }

    public Map<Integer, Light> getLights() {
        return lights;
    }

    public void setLights(Map<Integer, Light> lights) {
        this.lights = lights;
    }
}
