package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Button addRouteBtn = (Button) findViewById(R.id.add_route_btn);

        assert addRouteBtn != null;
        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyActivity.this, RouteActivity.class);
                startActivity(intent);
            }
        });

    }

    public void openHomeActivity(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
