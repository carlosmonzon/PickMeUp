package com.belatrix.pickmeup.rest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.belatrix.pickmeup.model.FirebaseResponse;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.RouteDto;

import java.util.Map;

/**
 * Created by angel on 23/10/16.
 */
public interface PickMeUpFirebaseClient {

    @POST("/routes.json")
    Call<FirebaseResponse> registerRoute(@Body MyRoute route);

    @GET("/routes.json")
    Call<Map<String,RouteDto>> getRoutes();

    @GET("/routes/{id}.json")
    Call<RouteDto> getRoute(@Path("id") String id);

    @PUT("/users/{userId}.json")
    Call<FirebaseResponse> registerUser(@Path("userId") String id, @Body MyUser user);

    @GET("/users.json")
    Call<Map<String,MyUser>> getUsers();

    @GET("/users/{id}.json")
    Call<MyUser> getUser(@Path("id") String id);

    @GET("/users.json")
    Call<MyUser> getUser(@Query("equalTo") String uid, @Query("orderBy") String key);

    @PUT("/routes/{routeId}/members/{userId}.json")
    Call<MyUser> joinToRoute(@Path("routeId") String routeId, @Path("userId") String id, @Body MyUser user);

    @DELETE("/routes/{id}.json")
    Call<RouteDto> deleteRoute(@Path("id") String id);

    @DELETE("/routes/{routeId}/members/{userId}.json")
    Call<MyUser> disjointFromRoute(@Path("routeId") String routeId, @Path("userId") String userId);
}
