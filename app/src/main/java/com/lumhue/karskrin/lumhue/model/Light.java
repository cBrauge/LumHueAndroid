package com.lumhue.karskrin.lumhue.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Light {
    @Expose
    public Integer duration = 10;
    @Expose
    public List<Lightscolor> lightscolors = new ArrayList<Lightscolor>();

    public Light() {
        this.duration = 10;
        lightscolors.add(new Lightscolor(0, "rgb(255,0, 0)", true, "#ff0000"));
        lightscolors.add(new Lightscolor(1, "rgb(0,255,0)", true, "#00ff00"));
        lightscolors.add(new Lightscolor(2, "rgb(0, 0, 255)", true, "#0000ff"));
    }
}
