package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.UserType;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class User {

    private int id;

    private String name;

    private String mail;

    private UserType userType;

    public User(int id, String name, String mail, UserType userType) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
