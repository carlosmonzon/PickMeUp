package com.belatrix.pickmeup.rest;

import com.belatrix.pickmeup.model.Credentials;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.Passenger;
import com.belatrix.pickmeup.model.Route;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by root on 30/06/16.
 */
public interface PickMeUpClient {

    @GET("api/passenger")
    Call<List<Passenger>> getPassengers();

    @GET("api/passenger/{id}")
    Call<Passenger> getPassengerById(@Path("id") int id);

    @POST("/api/passenger")
    Call<Passenger> registerPassenger(@Body Passenger passenger);

    @POST("/api/passenger/login")
    Call<Passenger> login(@Body Credentials credentials);

    @GET("/api/routes")
    Call<List<MyRoute>> getRoutes();

    @GET("/api/routes/{id}")
    Call<MyRoute> getRoute(@Path("id") int id);

    @POST("/api/routes")
    Call<MyRoute> registerRoute(@Body MyRoute route);

}
