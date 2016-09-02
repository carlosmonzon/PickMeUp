package com.belatrix.pickmeup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 29/06/16.
 */
public class Passenger {

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String userName;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("skype_id")
    private String skypeId;

    @SerializedName("cellphone")
    private String cellphone;

    public Passenger() {
        //empty constructor
    }

    public Passenger(int id, String userName, String firstName, String lastName, String email, String skypeId,
            String cellphone) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skypeId = skypeId;
        this.cellphone = cellphone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Passenger passenger = (Passenger) o;

        if (id != passenger.id) {
            return false;
        }
        if (userName != null ? !userName.equals(passenger.userName) : passenger.userName != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(passenger.firstName) : passenger.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(passenger.lastName) : passenger.lastName != null) {
            return false;
        }
        if (email != null ? !email.equals(passenger.email) : passenger.email != null) {
            return false;
        }
        if (skypeId != null ? !skypeId.equals(passenger.skypeId) : passenger.skypeId != null) {
            return false;
        }
        return cellphone != null ? cellphone.equals(passenger.cellphone) : passenger.cellphone == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (skypeId != null ? skypeId.hashCode() : 0);
        result = 31 * result + (cellphone != null ? cellphone.hashCode() : 0);
        return result;
    }
}
