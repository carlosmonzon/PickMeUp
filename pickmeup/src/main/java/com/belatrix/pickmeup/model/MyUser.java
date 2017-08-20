package com.belatrix.pickmeup.model;

import com.belatrix.pickmeup.enums.UserType;

import java.util.HashMap;


/**
 * Created by angel on 23/10/16.
 */
public class MyUser {

    private String id;

    private Integer cellphone;

    private String email;

    private String first_name;

    private String last_name;

    private String skype_id;

    private UserType userType;

    private String fcm_id;

    public MyUser() {

    }

    public MyUser(Integer cellphone, String email, String first_name, String last_name, String skype_id, UserType userType) {
        this.cellphone = cellphone;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.skype_id = skype_id;
        this.userType = userType;
    }

    public MyUser(Integer cellphone, String email, String first_name, String last_name, String skype_id){
        this.cellphone = cellphone;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.skype_id = skype_id;
    }

    public HashMap<String, String> getFcmIdBody() {
        HashMap<String, String> fcmBody = new HashMap<String, String>();
        fcmBody.put("fcm_id", this.fcm_id);

        return fcmBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCellphone() {
        return cellphone;
    }

    public void setCellphone(Integer cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSkype_id() {
        return skype_id;
    }

    public void setSkype_id(String skype_id) {
        this.skype_id = skype_id;
    }

    public UserType getUserType() { return userType; }

    public void setUserType(UserType userType) { this.userType = userType; }

    public String getFcm_id() { return fcm_id; }

    public void setFcm_id(String fcm_id) { this.fcm_id = fcm_id; }
}
