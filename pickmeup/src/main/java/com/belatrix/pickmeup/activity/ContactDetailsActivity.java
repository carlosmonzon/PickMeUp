package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.util.SharedPreferenceManager;

public class ContactDetailsActivity extends AppCompatActivity {
    private TextView cellphoneTil;
    private TextView skypeTil;
    private TextView mailTil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tUserName);

        cellphoneTil = (TextView) findViewById(R.id.tvNumber1);
        mailTil = (TextView) findViewById(R.id.tvNumber2);
        skypeTil = (TextView) findViewById(R.id.tvNumber3);

        MyUser mUser = SharedPreferenceManager.readMyUser(this);
        cellphoneTil.setText(mUser.getCellphone().toString());
        mailTil.setText(mUser.getEmail());
        skypeTil.setText(mUser.getSkype_id());
        System.out.print("");
        toolbar.setTitle(mUser.getFirst_name() + " " + mUser.getLast_name()
        );
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
