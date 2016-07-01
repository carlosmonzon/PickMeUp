package com.belatrix.pickmeup.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.belatrix.pickmeup.model.Credentials;

/**
 * Created by gzavaleta on 01/07/16.
 */
public class SharedPreferenceManager {

    public static void storePreferences(){

    }
    public static void checkPreferences(SharedPreferences sharedPref, Credentials credentials){

        if (credentials.getRemember()){
            saveCredentials(sharedPref, credentials);
        }else{
            saveCredentials(sharedPref, new Credentials(null,null,false));
        }
    }


    public static Credentials readCredentials(SharedPreferences sharedPref){
        Credentials credentials = new Credentials();
        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);
        Boolean remember = sharedPref.getBoolean("remember", false);

        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setRemember(remember);

       return credentials;
    }


    public static void saveCredentials(SharedPreferences sharedPref, Credentials credentials){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", credentials.getUsername());
        editor.putString("password", credentials.getPassword());
        editor.putBoolean("remember", credentials.getRemember());
        editor.commit();
    }
}
