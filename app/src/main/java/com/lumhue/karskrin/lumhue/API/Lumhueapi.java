package com.lumhue.karskrin.lumhue.API;

import com.lumhue.karskrin.lumhue.model.AmbianceApplyResponse;
import com.lumhue.karskrin.lumhue.model.AmbianceModel;
import com.lumhue.karskrin.lumhue.model.LoginResponseDTO;
import com.lumhue.karskrin.lumhue.model.LumHueBeaconModel;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;
import com.lumhue.karskrin.lumhue.model.Request;
import com.lumhue.karskrin.lumhue.model.RequestCreateAmbiance;
import com.lumhue.karskrin.lumhue.model.RequestPostLight;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface Lumhueapi {
    @GET("/lights")
        //here is the other url part.best way is to start using /
    void getLights(@Query("access_token") String token, Callback<List<Lumhuemodel>> response);
    //response is the response from the server which is now in the POJO
    @POST("/lights")
    //here is the other url part.best way is to start using /
    void postLights(@Body RequestPostLight request, Callback<Lumhuemodel> response);
    //response is the response from the server which is now in the POJO

    // Function to login
    @FormUrlEncoded
    @POST("/signin")
    void postLogin(@Field("email") String email, @Field("password") String password, Callback<LoginResponseDTO> response);

    // Function to register
    @FormUrlEncoded
    @POST("/signup")
    void postSignup(@Field("name") String name, @Field("email") String email, @Field("password") String password, Callback<LoginResponseDTO> response);

    //Get the ambiances
    @GET("/ambiance")
    void getAmbiances(@Query("access_token") String token, Callback<HashMap<String, AmbianceModel>> response);

    //Apply an ambiance
    @FormUrlEncoded
    @POST("/ambiance/apply")
    void applyAmbiance(@Field("access_token") String token, @Field("ambiance_id") String id, Callback<AmbianceApplyResponse> response);

    //Update an ambiance
    @POST("/ambiance/update")
    void updateAmbiance(@Body Request request, Callback<AmbianceApplyResponse> response);

    //Create an ambiance
    @POST("/ambiance/update")
    void createAmbiance(@Body RequestCreateAmbiance request, Callback<AmbianceApplyResponse> response);

    //Remove an ambiance
    @FormUrlEncoded
    @POST("/ambiance/remove")
    void removeAmbiance(@Field("access_token") String token, @Field("ambiance_id") String id, Callback<AmbianceApplyResponse> response);

    //Get the beacons
    @GET("/beacons/all")
    void getBeacons(@Query("access_token") String token, Callback<List<LumHueBeaconModel>> response);

    //Sync a beacon_dialog_layout.xml
    @FormUrlEncoded
    @POST("/beacons/sync")
    void syncBeacon(@Field("access_token") String token, @Field("beacon_id") String id, @Field("beacon_uuid") String uuid, @Field("data") Integer data, Callback<List<LumHueBeaconModel>> response);
}