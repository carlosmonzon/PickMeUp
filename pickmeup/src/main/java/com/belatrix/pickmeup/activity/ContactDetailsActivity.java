package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.gson.Gson;

public class ContactDetailsActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private TextView cellphoneTil;

    private TextView skypeTil;

    private TextView mailTil;

    private String cellphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tUserName);

        cellphoneTil = (TextView) findViewById(R.id.tvNumber1);
        mailTil = (TextView) findViewById(R.id.tvNumber2);
        skypeTil = (TextView) findViewById(R.id.tvNumber3);

        Gson gson = new Gson();
        Intent intent = getIntent();
        MyUser currentUser = gson.fromJson(intent.getStringExtra("userJson"), MyUser.class);

        if(currentUser==null){
            currentUser = SharedPreferenceManager.readMyUser(this);
        }
        cellphoneTil.setText(currentUser.getCellphone().toString());
        mailTil.setText(currentUser.getEmail());
        skypeTil.setText(currentUser.getSkype_id());
        cellphone = currentUser.getCellphone().toString();
        toolbar.setTitle(currentUser.getFirst_name() + " " + currentUser.getLast_name()
        );


        cellphoneTil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel: "+cellphone));
                Toast.makeText(ContactDetailsActivity.this, "Calling", Toast.LENGTH_SHORT).show();

            }
        });

        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
