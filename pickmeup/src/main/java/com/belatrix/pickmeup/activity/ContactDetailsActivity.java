package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactDetailsActivity extends AppCompatActivity {
    private TextView fullnameTil;
    private TextView cellphoneTil;
    private TextView skypeTil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        fullnameTil = (TextView) findViewById(R.id.user2_text);
        cellphoneTil = (TextView) findViewById(R.id.mobile2_text);
        skypeTil = (TextView) findViewById(R.id.skype2_text);

        PickMeUpFirebaseClient client = ServiceGenerator.createService(PickMeUpFirebaseClient.class);

        // TODO: Use real user key
        Call<MyUser> callPassengers = client.getUser("aJsmGzs9AzbrOThxc7xCHoJmhhX2");

        callPassengers.enqueue(new Callback<MyUser>() {
            @Override
            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        MyUser user = response.body();
                        fullnameTil.setText(user.getFirst_name() + " " + user.getLast_name());
                        cellphoneTil.setText(user.getCellphone().toString());
                        skypeTil.setText(user.getSkype_id().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyUser> call, Throwable t) {
                Log.e("ContactDetails.failure:", t.toString());
            }
        });
    }
}
