package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class Route {

    private int id;

    private Departure departure;

    private Destination destination;

    private User routeOwner;

    private String departureTime;

    private int placeAvailable;

    public Route(int id, Departure departure, Destination destination, User routeOwner, String departureTime,
            int placeAvailable) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
        this.routeOwner = routeOwner;
        this.departureTime = departureTime;
        this.placeAvailable = placeAvailable;
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
}