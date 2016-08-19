package com.belatrix.pickmeup.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.rest.PickMeUpClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class DetailPageActivity extends AppCompatActivity {

    private RelativeLayout rlLoading;
    private ScrollView svRoute;

    private TextInputEditText fromInput;
    private TextInputEditText toInput;
    private TextInputEditText costInput;
    private TextInputEditText departureTimeInput;
    private TextInputEditText contactInput;
    private TextInputEditText streetsInput;
    private TextInputEditText seatInput;
    private TextInputEditText availableInput;
    private TextInputEditText passengersInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        rlLoading = (RelativeLayout) findViewById(R.id.activity_detail_page_loading_rl);
        svRoute = (ScrollView) findViewById(R.id.activity_detail_page_route_sv);

        fromInput = (TextInputEditText) findViewById(R.id.from_tiet_data);
        toInput = (TextInputEditText) findViewById(R.id.to_tiet_data);
        costInput = (TextInputEditText) findViewById(R.id.cost_tiet_data);
        departureTimeInput = (TextInputEditText) findViewById(R.id.departure_time_tiet_data);
        contactInput = (TextInputEditText) findViewById(R.id.contact_tiet_data);
        streetsInput = (TextInputEditText) findViewById(R.id.streets_tiet_data);
        seatInput = (TextInputEditText) findViewById(R.id.seat_tiet_data);
        availableInput = (TextInputEditText) findViewById(R.id.available_tiet_data);
        passengersInput = (TextInputEditText) findViewById(R.id.passengers_tiet_data);

        Intent intent = getIntent();

        int routeId = intent.getIntExtra("routeId", 0);

        // For demo purposes
        if (routeId > 2)
            routeId = 1;

        Log.e("routeID", String.valueOf(routeId));
        Call<MyRoute> call = ServiceGenerator.createService(PickMeUpClient.class).getRoute(routeId);

        call.enqueue(new Callback<MyRoute>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MyRoute> call, Response<MyRoute> response) {
                rlLoading.setVisibility(View.GONE);
                svRoute.setVisibility(View.VISIBLE);
                Log.d("[1]", "--->");
                MyRoute route = response.body();
                Log.d("[2]", "--->");
                fromInput.setText(route.getDeparture());
                Log.d("[3]", "--->");
                fromInput.setEnabled(false);
                Log.d("[4]", "--->");
                toInput.setText(route.getArrival());
                Log.d("[5]", "--->");
                costInput.setText("");
                Log.d("[6]", "--->");
                departureTimeInput.setText("");
                Log.d("[7]", "--->");
                contactInput.setText(route.getContact());
                Log.d("[8]", "--->");
                streetsInput.setText("");
                Log.d("[9]", "--->");
                seatInput.setText(String.valueOf(route.getSits()));
                Log.d("[10]", "--->");
                availableInput.setText("");
                Log.d("[11]", "--->");
                passengersInput.setText("");
                Log.d("[12]", "--->");
            }

            @Override
            public void onFailure(Call<MyRoute> call, Throwable t) {
                Log.e("[error]", t.toString());
                Toast.makeText(DetailPageActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
