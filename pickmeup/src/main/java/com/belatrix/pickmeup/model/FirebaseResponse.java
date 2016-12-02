package com.belatrix.pickmeup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gzavaleta on 22/11/16.
 */
public class FirebaseResponse {

    @SerializedName("name")
    private String name;

    public FirebaseResponse() {
        //empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
