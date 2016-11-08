package com.belatrix.pickmeup.rest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.RouteDto;

import java.util.Map;

/**
 * Created by angel on 23/10/16.
 */
public interface PickMeUpFirebaseClient {

    @POST("/routes.json")
    Call<RouteDto> registerRoute(@Body MyRoute route);

    @GET("/routes.json")
    Call<Map<String,RouteDto>> getRoutes();

    @GET("/routes/{id}.json")
    Call<RouteDto> getRoute(@Path("id") String id);

    @GET("/users.json")
    Call<Map<String,MyUser>> getUsers();

    @GET("/users/{id}.json")
    Call<MyUser> getUser(@Path("id") String id);

    @PUT("/routes/{routeId}/members/{userId}.json")
    Call<MyUser> joinToRoute(@Path("routeId") String routeId, @Path("userId") String id, @Body MyUser user);
}
