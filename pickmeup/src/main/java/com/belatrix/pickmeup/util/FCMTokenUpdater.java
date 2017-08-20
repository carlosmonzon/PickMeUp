package com.belatrix.pickmeup.util;

import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by moogriento on 8/20/17.
 */

public class FCMTokenUpdater {

    private static final String TAG = "FCMTokenUpdater";

    public static void updateToken (MyUser user) {

        if (user != null) {

            Call<MyUser> call =
                    ServiceGenerator
                            .createService(PickMeUpFirebaseClient.class)
                            .setFCMId(user.getId(), user.getFcmIdBody());

            call.enqueue(new Callback<MyUser>() {

                @Override
                public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                    Log.d(TAG, "FCM token successfully updated");
                }

                @Override
                public void onFailure(Call<MyUser> call, Throwable t) {
                    Log.e(TAG, "FCM token failed: " + t.getMessage());
                }
            });
        }
    }
}

