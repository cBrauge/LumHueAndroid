package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karskrin on 06/06/2016.
 */
public class Light {
    @Expose
    public Integer duration = 10;
    @Expose
    public List<Lightscolor> lightscolors = new ArrayList<Lightscolor>();

    public Light() {
        this.duration = 10;
        for (int i = 0; i < 3; i++)
            lightscolors.add(new Lightscolor(i, "rgb(255,255,255)", true, "#ffffff"));
    }
}
