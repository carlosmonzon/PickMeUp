package com.belatrix.pickmeup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gzavaleta on 01/07/16.
 */
public class Credentials {

    @SerializedName("email")
    private String username;

    @SerializedName("password")
    private String password;

    private Boolean remember;

    public Credentials() {
        // empty constructor
    }

    public Credentials(String username, String password, Boolean remember) {
        this.username = username;
        this.password = password;
        this.setRemember(remember);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}
