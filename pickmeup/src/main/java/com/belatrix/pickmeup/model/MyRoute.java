package com.belatrix.pickmeup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moogriento on 6/30/16.
 */
public class MyRoute {

    private String arrival;

    private MyContact contact;

    private String departure;

    @SerializedName("pk")
    private int id;

    private int sits;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public MyContact getContact() {
        return contact;
    }

    public void setContact(MyContact contact) {
        this.contact = contact;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSits() {
        return sits;
    }

    public void setSits(int sits) {
        this.sits = sits;
    }
}
