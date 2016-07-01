package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class Route {

    private int id;

    private Departure departure;

    private Destination destination;

    private Double cost;

    private PaymentType paymentType;

    private User routeOwner;

    private String departureTime;

    private int placeAvailable;

    private String addressDestination;

    public Route(){
        //empty constructor
    }
    public Route(int id, Departure departure, Destination destination, User routeOwner, String departureTime,
            int placeAvailable) {
        this.setId(id);
        this.setDeparture(departure);
        this.setDestination(destination);
        this.setRouteOwner(routeOwner);
        this.setDepartureTime(departureTime);
        this.setPlaceAvailable(placeAvailable);
    }

    public Route(Departure departure, Destination destination, Double cost, PaymentType paymentType, User routeOwner, String departureTime,
                 String addressDestination) {
        this.setDeparture(departure);
        this.setDestination(destination);
        this.setCost(cost);
        this.setPaymentType(paymentType);
        this.setRouteOwner(routeOwner);
        this.setDepartureTime(departureTime);
        this.setAddressDestination(addressDestination);
    }


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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }


    public User getRouteOwner() {
        return routeOwner;
    }

    public void setRouteOwner(User routeOwner) {
        this.routeOwner = routeOwner;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getPlaceAvailable() {
        return placeAvailable;
    }

    public void setPlaceAvailable(int placeAvailable) {
        this.placeAvailable = placeAvailable;
    }

    public String getAddressDestination() {
        return addressDestination;
    }

    public void setAddressDestination(String addressDestination) {
        this.addressDestination = addressDestination;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
