package com.belatrix.pickmeup.rest;

import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.Passenger;

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

    //@GET("api/passenger/list")
    @GET("passenger.json?print=pretty")
    Call<List<Passenger>> getPassengers();

    //@GET("api/passenger/{passenger_id}")
    @GET("passenger/{passenger_id}.json?print=pretty")
    Call<Passenger> getPassengerById(@Path("passenger_id") int id);

    //@POST("/api/passenger/register")
    Call<Passenger> registerPassenger(@Body Passenger passenger);

    @GET("routes/{id}.json")
    Call<MyRoute> getRoute(@Path("id") int id);
}
