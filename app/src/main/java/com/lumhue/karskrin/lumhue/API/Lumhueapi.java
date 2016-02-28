package com.lumhue.karskrin.lumhue.API;

import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Karskrin on 27/02/2016.
 */
public interface Lumhueapi {
    @GET("/lights")
        //here is the other url part.best way is to start using /
    void getLights(Callback<List<Lumhuemodel>> response);
    //response is the response from the server which is now in the POJO
    @POST("/lights")
    //here is the other url part.best way is to start using /
    void postLights(@Body Integer id, @Body status, Callback<List<Lumhuemodel>> response);
    //response is the response from the server which is now in the POJO
}