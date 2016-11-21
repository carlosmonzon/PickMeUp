package com.belatrix.pickmeup.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.belatrix.pickmeup.model.Credentials;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.Passenger;

import com.google.gson.Gson;

/**
 * Created by gzavaleta on 01/07/16.
 */
public class SharedPreferenceManager {

    public static MyUser readMyUser(@NonNull Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        return gson.fromJson(sharedPref.getString("myUser", ""), MyUser.class);
    }

    public static void saveMyUser(@NonNull Context context, @NonNull MyUser myUser) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();
        editor.putString("myUser", gson.toJson(myUser));
        editor.apply();
    }
}
