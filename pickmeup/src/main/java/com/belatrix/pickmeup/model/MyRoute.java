package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moogriento on 6/30/16.
 */
public class MyRoute {

    @SerializedName("id")
    private int id;

    @SerializedName("departure")
    private Departure departure;

    @SerializedName("destination")
    private Destination destination;

    @SerializedName("cost")
    private Double cost;

    @SerializedName("paymentType")
    private PaymentType paymentType;

    @SerializedName("routeOwner")
    private int routeOwner;

    @SerializedName("departureTime")
    private String departureTime;

    @SerializedName("placeAvailable")
    private int placeAvailable;

    @SerializedName("addressDestination")
    private String addressDestination;

    @SerializedName("passengers")
    private int[] passengers;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Double getCost() {  return cost; }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public int getRouteOwner() {
        return routeOwner;
    }

    public void setRouteOwner(int routeOwner) {
        this.routeOwner = routeOwner;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPlaceAvailable() { return placeAvailable; }

    public void setPlaceAvailable(int placeAvailable) { this.placeAvailable = placeAvailable;  }

    public String getAddressDestination() { return addressDestination; }

    public void setAddressDestination(String addressDestination) {
        this.addressDestination = addressDestination;
    }

    public int[] getPassengers() { return passengers; }

    public void setPassengers(int[] passengers) { this.passengers = passengers; }
}
