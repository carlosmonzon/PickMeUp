package com.belatrix.pickmeup.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.util.FCMTokenUpdater;
import com.belatrix.pickmeup.util.SharedPreferenceManager;

import android.util.Log;

public class FIIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "TO SERVER: " + token);

        MyUser user = SharedPreferenceManager.readMyUser(this);

        if (user != null) {
            user.setFcm_id(token);
            FCMTokenUpdater.updateToken(user);
        }
    }
}
