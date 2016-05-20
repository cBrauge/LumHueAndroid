package com.lumhue.karskrin.lumhue.API;

import com.lumhue.karskrin.lumhue.model.LoginResponseDTO;
import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

public interface Lumhueapi {
    @GET("/lights")
        //here is the other url part.best way is to start using /
    void getLights(@Query("access_token") String token, Callback<List<Lumhuemodel>> response);
    //response is the response from the server which is now in the POJO
    @Multipart
    @POST("/lights")
    //here is the other url part.best way is to start using /
    void postLights(@Query("access_token") String token, @Query("color") String rgb, @Part("id") Integer id, @Query("on") Boolean on, Callback<Lumhuemodel> response);
    //response is the response from the server which is now in the POJO

    // Function to login
    @FormUrlEncoded
    @POST("/signin")
    void postLogin(@Field("email") String email, @Field("password") String password, Callback<LoginResponseDTO> response);

    // Function to register
    @FormUrlEncoded
    @POST("/signup")
    void postSignup(@Field("name") String name, @Field("email") String email, @Field("password") String password, Callback<LoginResponseDTO> response);
}