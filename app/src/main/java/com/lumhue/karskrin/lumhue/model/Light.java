package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Light {
    @SerializedName("state")
    @Expose
    private State state;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("modelid")
    @Expose
    private String modelid;
    @SerializedName("manufacturername")
    @Expose
    private String manufacturername;
    @SerializedName("uniqueid")
    @Expose
    private String uniqueid;
    @SerializedName("swversion")
    @Expose
    private String swversion;

    /**
     * @return The state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The modelid
     */
    public String getModelid() {
        return modelid;
    }

    /**
     * @param modelid The modelid
     */
    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    /**
     * @return The manufacturername
     */
    public String getManufacturername() {
        return manufacturername;
    }

    /**
     * @param manufacturername The manufacturername
     */
    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    /**
     * @return The uniqueid
     */
    public String getUniqueid() {
        return uniqueid;
    }

    /**
     * @param uniqueid The uniqueid
     */
    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    /**
     * @return The swversion
     */
    public String getSwversion() {
        return swversion;
    }

    /**
     * @param swversion The swversion
     */
    public void setSwversion(String swversion) {
        this.swversion = swversion;
    }

}