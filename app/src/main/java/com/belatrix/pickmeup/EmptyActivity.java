package com.belatrix.pickmeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
    }

    public void openHomeActivity (View view){

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
