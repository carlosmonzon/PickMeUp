package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;
import com.belatrix.pickmeup.fragment.DatePickerFragment;
import com.belatrix.pickmeup.fragment.TimePickerFragment;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.TimePicked;
import com.belatrix.pickmeup.rest.PickMeUpClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity
        implements TimePickerFragment.TimeSelected, DatePickerFragment.DateSelected {

    private Spinner paymentMethodSpn;

    private Spinner departureSpn;

    private Spinner destinationSpn;

    private Button addRouteBtn;

    private Button joinRouteBtn;

    private TextView fromTil;

    private TextView toTil;

    private TextInputLayout costTil;

    private TextInputEditText costTiet;

    private TextInputLayout departureTimeTil;

    private TextInputEditText departureTimeTiet;

    private TextInputLayout contactTil;

    private TextInputEditText contactTiet;

    private TextInputLayout streetsTil;

    private TextInputEditText streetsTiet;

    private TimePicked timePicked = new TimePicked();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        departureSpn = (Spinner) findViewById(R.id.departure_spn);
        destinationSpn = (Spinner) findViewById(R.id.destination_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);
        joinRouteBtn = (Button) findViewById(R.id.join_btn);
        fromTil = (TextView) findViewById(R.id.from_til);
        toTil = (TextView) findViewById(R.id.to_til);
        costTil = (TextInputLayout) findViewById(R.id.cost_til);
        costTiet = (TextInputEditText) findViewById(R.id.cost_tiet);
        departureTimeTil = (TextInputLayout) findViewById(R.id.departure_time_til);
        departureTimeTiet = (TextInputEditText) findViewById(R.id.departure_time_tiet);

        contactTil = (TextInputLayout) findViewById(R.id.contact_til);
        contactTiet = (TextInputEditText) findViewById(R.id.contact_tiet);
        streetsTil = (TextInputLayout) findViewById(R.id.streets_til);
        streetsTiet = (TextInputEditText) findViewById(R.id.streets_tiet);

        //set data to Lists
        Intent intent = getIntent();

        int routeId = intent.getIntExtra("routeId", 0);

        if (routeId != 0) {
            getRoute(routeId);
        } else {
            setLists();
        }

        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRoute();

            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void validateRoute() {
        boolean hasError = false;
        //Route newRoute = new Route();
        MyRoute newRoute = new MyRoute();

        fromTil.setError(null);
        toTil.setError(null);
        costTil.setError(null);
        departureTimeTil.setError(null);
        contactTil.setError(null);
        streetsTil.setError(null);

        newRoute.setDeparture(Departure.getValue(departureSpn.getSelectedItem().toString()));

        newRoute.setDestination(Destination.getValue(destinationSpn.getSelectedItem().toString()));

        newRoute.setPaymentType(PaymentType.getValue(paymentMethodSpn.getSelectedItem().toString()));

        try {
            Double cost = Double.parseDouble(costTiet.getText().toString());
            newRoute.setCost(cost);
        } catch (Exception e) {
            costTil.setError(getResources().getString(R.string.add_route_cost_empty_error));
            hasError = true;
        }
        newRoute.setDepartureTime(String.valueOf(new Date().getTime()));

        if (contactTiet.getText().toString().trim().equals("")) {
            contactTil.setError(getResources().getString(R.string.add_route_contact_empty_error));
            hasError = true;
        } else {
            //TODO: we need to set the logged in user as default
            //newRoute.setRouteOwner(new User(0,"Example", "example@example.com", UserType.OWNER));
            newRoute.setRouteOwner(1);
        }

        if (departureTimeTiet.getText().equals("")) {
            streetsTil.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        } else {
            newRoute.setDepartureTime(Long.toString(timePicked.timePickedToMiliseconds()));
        }

        if (streetsTiet.getText().toString().trim().equals("")) {
            streetsTil.setError(getResources().getString(R.string.add_route_streets_empty_error));
            hasError = true;
        } else {
            //newRoute.setAddressDestination(streetsTiet.getText().toString());
            newRoute.setAddressDestination(streetsTiet.getText().toString());
        }

        if (hasError) {
            Toast.makeText(RouteActivity.this, getResources().getString(R.string.add_route_form_error),
                    Toast.LENGTH_SHORT).show();
        } else {
            saveRoute(newRoute);
        }

        // call to save the new route

    }

    public void populateSpinner(Spinner spinner, List<String> list) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void populateSpinner(Spinner spinner, List<String> list, String valueString) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(valueString);
        spinner.setSelection(spinnerPosition);

    }


    public void setLists() {
        populateSpinner(paymentMethodSpn, PaymentType.getList());
        populateSpinner(departureSpn, Departure.getList());
        populateSpinner(destinationSpn, Destination.getList());
        joinRouteBtn.setVisibility(View.GONE);
    }

    public void setListsData(MyRoute route) {
        populateSpinner(paymentMethodSpn, PaymentType.getList(), route.getPaymentType().toString());
        populateSpinner(departureSpn, Departure.getList(), route.getDeparture().toString());
        populateSpinner(destinationSpn, Destination.getList(), route.getDestination().toString());
        costTiet.setText(route.getCost().toString());
        departureTimeTiet.setText(route.getDepartureTime().toString());
        contactTiet.setText(route.getRouteOwner() + "");
        streetsTiet.setText(route.getAddressDestination());
        addRouteBtn.setVisibility(View.GONE);

        paymentMethodSpn.setFocusable(false);
        departureSpn.setFocusable(false);
        destinationSpn.setFocusable(false);
        costTiet.setFocusable(false);
        departureTimeTiet.setFocusable(false);
        contactTiet.setFocusable(false);
        streetsTiet.setFocusable(false);
        addRouteBtn.setFocusable(false);
    }

    public void saveRoute(MyRoute route) {
        Call<MyRoute> call = ServiceGenerator.createService(PickMeUpClient.class).registerRoute(route);

        call.enqueue(new Callback<MyRoute>() {
            @Override
            public void onResponse(Call<MyRoute> call, Response<MyRoute> response) {
                Toast.makeText(RouteActivity.this, "ok",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyRoute> call, Throwable t) {
                Log.e("Failure saveRoute", t.toString());
            }
        });
    }

    public void getRoute(int routeId) {
        Call<MyRoute> call = ServiceGenerator.createService(PickMeUpClient.class).getRoute(routeId);

        call.enqueue(new Callback<MyRoute>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MyRoute> call, Response<MyRoute> response) {

                MyRoute route = response.body();
                setListsData(route);
            }

            @Override
            public void onFailure(Call<MyRoute> call, Throwable t) {
                Toast.makeText(RouteActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getTime(int hourOfDay, int minute) {
        //set hour and minute
        timePicked.setHourOfDay(hourOfDay);
        timePicked.setMinute(minute);
        //call date fragment
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void getDate(int year, int month, int day) {
        //set year, month and day
        timePicked.setYear(year);
        timePicked.setMonth(month);
        timePicked.setDay(day);
        //assign to text
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, (int) timePicked.timePickedToMiliseconds());
        departureTimeTiet.setText(cal.getTime().toString());
    }

}