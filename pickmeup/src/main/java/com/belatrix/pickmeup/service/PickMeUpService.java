package com.belatrix.pickmeup.service;

import com.belatrix.pickmeup.model.MyRoute;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by moogriento on 6/30/16.
 */
public interface PickMeUpService {

    @GET("routes/{id}.json")
    Call<MyRoute> getRoute(@Path("id") int id);

}
