package com.belatrix.pickmeup.model;

/**
 * Created by gzavaleta on 01/12/16.
 */

public class MyUserCredentials {

    private String email;

    private String password;

    private Boolean isChecked;

    public MyUserCredentials(String email, String password, boolean isChecked) {
        this.email = email;
        this.password = password;
        this.isChecked = isChecked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
