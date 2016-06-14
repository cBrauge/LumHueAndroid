package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karskrin on 06/06/2016.
 */
public class Ambiance {
    @Expose
    public String name;
    @Expose
    public List<Light> lights = new ArrayList<Light>();
    @Expose
    public String uniq_id;
}
