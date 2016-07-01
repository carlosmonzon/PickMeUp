package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.service.PickMeUpService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPageActivity extends AppCompatActivity implements Callback<MyRoute> {

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pickmeup-belatrix.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PickMeUpService pickMeUpService = retrofit.create(PickMeUpService.class);

        Call<MyRoute> call = pickMeUpService.getRoute(routeId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MyRoute> call, Response<MyRoute> response) {
        rlLoading.setVisibility(View.GONE);
        svRoute.setVisibility(View.VISIBLE);

        MyRoute route = response.body();

        fromInput.setText(route.getDeparture());
        fromInput.setEnabled(false);
        toInput.setText(route.getArrival());
        costInput.setText("");
        departureTimeInput.setText("");
        contactInput.setText(route.getContact().getFirstName() + " " + route.getContact().getLastName());
        streetsInput.setText("");
        seatInput.setText(String.valueOf(route.getSits()));
        availableInput.setText("");
        passengersInput.setText("");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Toast.makeText(DetailPageActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
