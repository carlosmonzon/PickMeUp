package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.belatrix.pickmeup.R;

public class EmptyActivity extends AppCompatActivity {

    private Button addRouteBtn;

    private Button showPageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        addRouteBtn = (Button)findViewById(R.id.add_route_btn);

        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
        });

        showPageDetail = (Button) findViewById(R.id.page_detail_btn);
        showPageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmptyActivity.this, DetailPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
