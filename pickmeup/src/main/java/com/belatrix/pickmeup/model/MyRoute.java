package com.belatrix.pickmeup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moogriento on 6/30/16.
 */
public class MyRoute {

    private String arrival;

    private MyContact contact;

    private String departure;

    private String streets;

    private double cost;

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

    public double getCost() {
        return cost;
    }

    public void setCost(double sits) {
        this.cost = cost;
    }

    public String getStreets() {
        return streets;
    }

    public void setStreets(String streets) {
        this.streets = streets;
    }
}
