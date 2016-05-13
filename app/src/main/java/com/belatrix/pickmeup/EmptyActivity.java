package com.belatrix.pickmeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                Intent intent = new Intent(EmptyActivity.this, AddRoute.class);
                startActivity(intent);
            }
        });
    }

    public void openHomeActivity (View view){

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
