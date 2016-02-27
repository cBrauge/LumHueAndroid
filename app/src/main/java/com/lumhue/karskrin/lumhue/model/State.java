package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class State {

    @SerializedName("on")
    @Expose
    private boolean on;
    @SerializedName("bri")
    @Expose
    private float bri;
    @SerializedName("hue")
    @Expose
    private int hue;
    @SerializedName("sat")
    @Expose
    private float sat;
    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("xy")
    @Expose
    private List<Double> xy = new ArrayList<Double>();
    @SerializedName("ct")
    @Expose
    private int ct;
    @SerializedName("alert")
    @Expose
    private String alert;
    @SerializedName("colormode")
    @Expose
    private String colormode;
    @SerializedName("reachable")
    @Expose
    private boolean reachable;

    /**
     * @return The on
     */
    public boolean isOn() {
        return on;
    }

    /**
     * @param on The on
     */
    public void setOn(boolean on) {
        this.on = on;
    }

    /**
     * @return The bri
     */
    public float getBri() {
        return bri;
    }

    /**
     * @param bri The bri
     */
    public void setBri(float bri) {
        this.bri = bri;
    }

    /**
     * @return The hue
     */
    public int getHue() {
        return hue;
    }

    /**
     * @param hue The hue
     */
    public void setHue(int hue) {
        this.hue = hue;
    }

    /**
     * @return The sat
     */
    public float getSat() {
        return sat;
    }

    /**
     * @param sat The sat
     */
    public void setSat(float sat) {
        this.sat = sat;
    }

    /**
     * @return The effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * @param effect The effect
     */
    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * @return The xy
     */
    public List<Double> getXy() {
        return xy;
    }

    /**
     * @param xy The xy
     */
    public void setXy(List<Double> xy) {
        this.xy = xy;
    }

    /**
     * @return The ct
     */
    public int getCt() {
        return ct;
    }

    /**
     * @param ct The ct
     */
    public void setCt(int ct) {
        this.ct = ct;
    }

    /**
     * @return The alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert The alert
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }

    /**
     * @return The colormode
     */
    public String getColormode() {
        return colormode;
    }

    /**
     * @param colormode The colormode
     */
    public void setColormode(String colormode) {
        this.colormode = colormode;
    }

    /**
     * @return The reachable
     */
    public boolean isReachable() {
        return reachable;
    }

    /**
     * @param reachable The reachable
     */
    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

}