package com.lumhue.karskrin.lumhue.API;

import com.lumhue.karskrin.lumhue.model.Lumhuemodel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Karskrin on 27/02/2016.
 */
public interface Lumhueapi {
    @GET("/getbridge?token=alhzT29HZFptWEFtTTlNaTVGYlYvUHRCVHJHYjNPUWF3S2NSTjY0Znc3ND0%3D&bridgeid=001788fffe260c4a")
        //here is the other url part.best way is to start using /
    void getFeed(Callback<Lumhuemodel> response);
    //response is the response from the server which is now in the POJO
}