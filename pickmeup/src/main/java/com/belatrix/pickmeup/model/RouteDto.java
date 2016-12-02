package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by angel on 23/10/16.
 */
public class RouteDto {

    @SerializedName("departure")
    private Departure departure;

    @SerializedName("destination")
    private Destination destination;

    @SerializedName("cost")
    private Double cost;

    @SerializedName("paymentType")
    private PaymentType paymentType;

    @SerializedName("departureTime")
    private String departureTime;

    @SerializedName("placeAvailable")
    private int placeAvailable;

    @SerializedName("addressDestination")
    private String addressDestination;

    @SerializedName("members")
    private Map<String, MyUser> members;

    @SerializedName("owner")
    private MyUser owner;


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

    public Map<String, MyUser> getMembers() { return members; }

    public void setMembers(Map<String, MyUser> members) { this.members = members; }

    public MyUser getOwner() { return owner; }

    public void setOwner(MyUser owner) { this.owner = owner; }
}
