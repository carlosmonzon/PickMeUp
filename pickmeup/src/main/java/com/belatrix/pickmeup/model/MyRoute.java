package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moogriento on 6/30/16.
 */
public class MyRoute extends RouteDto{

    @SerializedName("id")
    private String id;

    @SerializedName("passengers")
    private List<MyUser> passengers;

    public MyRoute() {
        //Empty constructor
    }

    public MyRoute(RouteDto routeDto) {
        if (null != routeDto.getAddressDestination()) {
            setAddressDestination(routeDto.getAddressDestination());
        }
        if (null != routeDto.getCost()) {
            setCost(routeDto.getCost());
        }
        if (null != routeDto.getDeparture()) {
            setDeparture(routeDto.getDeparture());
        }
        if (null != routeDto.getDepartureTime()) {
            setDepartureTime(routeDto.getDepartureTime());
        }
        if (null != routeDto.getDestination()) {
            setDestination(routeDto.getDestination());
        }
        if (null != routeDto.getOwner()) {
            setOwner(routeDto.getOwner());
        }
        if (null != routeDto.getPaymentType()) {
            setPaymentType(routeDto.getPaymentType());
        }
        setPlaceAvailable(routeDto.getPlaceAvailable());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MyUser> getPassengers() { return passengers; }

    public void setPassengers(List<MyUser> passengers) { this.passengers = passengers; }

}
