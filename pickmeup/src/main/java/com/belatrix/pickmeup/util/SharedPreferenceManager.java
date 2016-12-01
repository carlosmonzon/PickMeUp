package com.belatrix.pickmeup.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.MyUserCredentials;
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

    public static MyUserCredentials readMyUserCredentials(@NonNull Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        return gson.fromJson(sharedPref.getString("myUserCredentials", ""), MyUserCredentials.class);
    }

    public static void saveMyUserCredentials(@NonNull Context context, String user, String password,
                                             boolean isChecked) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();
        MyUserCredentials myUserCredentials = new MyUserCredentials(user, password, isChecked);
        editor.putString("myUserCredentials", gson.toJson(myUserCredentials));
        editor.apply();
    }
}
