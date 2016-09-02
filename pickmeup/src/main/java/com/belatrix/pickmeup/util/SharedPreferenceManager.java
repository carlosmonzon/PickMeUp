package com.belatrix.pickmeup.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.belatrix.pickmeup.model.Credentials;
import com.belatrix.pickmeup.model.Passenger;

import com.google.gson.Gson;

/**
 * Created by gzavaleta on 01/07/16.
 */
public class SharedPreferenceManager {

    public static void checkPreferences(@NonNull Context context, @NonNull Credentials credentials) {

        if (credentials.getRemember()) {
            saveCredentials(context, credentials);
        } else {
            saveCredentials(context, new Credentials(null, null, false));
        }
    }

    public static Credentials readCredentials(@NonNull Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Credentials credentials = new Credentials();
        String username = sharedPref.getString("username", null);
        String password = sharedPref.getString("password", null);
        Boolean remember = sharedPref.getBoolean("remember", false);

        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setRemember(remember);

        return credentials;
    }


    public static void saveCredentials(@NonNull Context context, @NonNull Credentials credentials) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("username", credentials.getUsername());
        editor.putString("password", credentials.getPassword());
        editor.putBoolean("remember", credentials.getRemember());
        editor.apply();
    }

    public static Passenger readPassenger(@NonNull Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        return gson.fromJson(sharedPref.getString("passenger", ""), Passenger.class);
    }

    public static void savePassenger(@NonNull Context context, @NonNull Passenger passenger) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();
        editor.putString("passenger", gson.toJson(passenger));
        editor.apply();
    }
}
