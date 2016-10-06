package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LumHueBeaconModel {

    @SerializedName("item_name")
    @Expose
    public String itemName;
    @SerializedName("item_type")
    @Expose
    public Integer itemType;
    @SerializedName("model_url")
    @Expose
    public String modelUrl;
    @SerializedName("xpos")
    @Expose
    public Float xpos;
    @SerializedName("ypos")
    @Expose
    public Float ypos;
    @SerializedName("zpos")
    @Expose
    public Float zpos;
    @SerializedName("rotation")
    @Expose
    public Float rotation;
    @SerializedName("scale_x")
    @Expose
    public Integer scaleX;
    @SerializedName("scale_y")
    @Expose
    public Integer scaleY;
    @SerializedName("scale_z")
    @Expose
    public Integer scaleZ;
    @SerializedName("fixed")
    @Expose
    public Boolean fixed;
    @SerializedName("enterLight")
    @Expose
    public List<Integer> enterLight = new ArrayList<Integer>();
    @SerializedName("leaveLight")
    @Expose
    public List<Integer> leaveLight = new ArrayList<Integer>();
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("lh_id")
    @Expose
    public String lh_id;
    @SerializedName("isSync")
    @Expose
    public Boolean isSync;
    @SerializedName("data")
    @Expose
    public Integer data;
}