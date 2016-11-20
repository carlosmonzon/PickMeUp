package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;
import com.belatrix.pickmeup.fragment.DatePickerFragment;
import com.belatrix.pickmeup.fragment.TimePickerFragment;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.Passenger;
import com.belatrix.pickmeup.model.RouteDto;
import com.belatrix.pickmeup.model.TimePicked;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.DataConverter;
import com.belatrix.pickmeup.util.SharedPreferenceManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
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
    private Button deleteRouteBtn;
    private Button disjointRouteBtn;
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
    private TextInputLayout passengerMaxCapacityTil;
    private TextInputEditText passengerMaxCapacityTiet;
    private MyUser mUser;
    private TimePicked timePicked = new TimePicked();
    String routeId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        mUser = SharedPreferenceManager.readMyUser(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        departureSpn = (Spinner) findViewById(R.id.departure_spn);
        destinationSpn = (Spinner) findViewById(R.id.destination_spn);
        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);

        joinRouteBtn = (Button) findViewById(R.id.join_btn);
        deleteRouteBtn = (Button) findViewById(R.id.delete_route_btn);
        disjointRouteBtn = (Button) findViewById(R.id.disjoint_btn);

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

        passengerMaxCapacityTil = (TextInputLayout) findViewById(R.id.passenger_max_capacity_til);
        passengerMaxCapacityTiet = (TextInputEditText) findViewById(R.id.passenger_max_capacity_tiet);

        contactTiet.setText(mUser.getFirst_name() +" "+ mUser.getLast_name());
        contactTiet.setEnabled(false);

        //set data to Lists
        Intent intent = getIntent();
        routeId = intent.getStringExtra("routeId");

        if (null != routeId && !routeId.isEmpty()) {
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
        joinRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinToRoute();
            }
        });
        deleteRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoute();
            }
        });
        disjointRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disjointFromRoute();
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void validateRoute() {
        boolean hasError = false;
        MyRoute newRoute = new MyRoute();

        //TODO
        int[] passengers = {1,2};

        fromTil.setError(null);
        toTil.setError(null);
        costTil.setError(null);
        departureTimeTil.setError(null);
        contactTil.setError(null);
        streetsTil.setError(null);

        newRoute.setDeparture(Departure.getValue(departureSpn.getSelectedItem().toString()));

        newRoute.setDestination(Destination.getValue(destinationSpn.getSelectedItem().toString()));

        newRoute.setPaymentType(PaymentType.getValue(paymentMethodSpn.getSelectedItem().toString()));

        //TODO
        newRoute.setPassengers(null);

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
            newRoute.setOwner(mUser.getId() + "");
        }

        if (departureTimeTiet.getText().equals("")) {
            streetsTil.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        } else {
            newRoute.setDepartureTime(Long.toString(timePicked.timePickedToMiliseconds()));
        }

        if (passengerMaxCapacityTiet.getText().equals("")) {
            passengerMaxCapacityTil.setError(getResources().getString(R.string.add_route_passengers_max_capacity_empty_error));
            hasError = true;
        } else {
            newRoute.setPlaceAvailable(Integer.parseInt(passengerMaxCapacityTiet.getText().toString()));
        }

        newRoute.setAddressDestination(streetsTiet.getText().toString());



        if (hasError) {
            Toast.makeText(RouteActivity.this, getResources().getString(R.string.add_route_form_error),
                    Toast.LENGTH_SHORT).show();
        } else {
            saveRoute(newRoute);
        }
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
        contactTiet.setText(route.getOwner());//needs work
        streetsTiet.setText(route.getAddressDestination());
        passengerMaxCapacityTiet.setText(route.getPlaceAvailable()+"");
        addRouteBtn.setVisibility(View.GONE);

        paymentMethodSpn.setFocusable(false);
        departureSpn.setFocusable(false);
        destinationSpn.setFocusable(false);
        costTiet.setFocusable(false);
        departureTimeTiet.setFocusable(false);
        contactTiet.setFocusable(false);
        streetsTiet.setFocusable(false);
        passengerMaxCapacityTiet.setFocusable(false);
        addRouteBtn.setFocusable(false);

        if (mUser == null)
            return;

        if (route.getOwner().equals(mUser.getId())) {
            deleteRouteBtn.setVisibility(View.VISIBLE);
            disjointRouteBtn.setVisibility(View.GONE);
        } else {

            for (MyUser user : route.getPassengers()) {
                if (user.getId().equals(mUser.getId())) {
                    disjointRouteBtn.setVisibility(View.VISIBLE);
                    joinRouteBtn.setVisibility(View.GONE);
                    break;
                }
            }

        }
    }

    public void saveRoute(MyRoute route) {
        Call<RouteDto> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).registerRoute(route);

        call.enqueue(new Callback<RouteDto>() {
            @Override
            public void onResponse(Call<RouteDto> call, Response<RouteDto> response) {
                try
                {
                    Toast.makeText(RouteActivity.this, "Route has been successfully created",
                            Toast.LENGTH_SHORT).show();
                    Intent k = new Intent(RouteActivity.this, HomeActivity.class);
                    startActivity(k);
                }catch(Exception e){

                }
            }

            @Override
            public void onFailure(Call<RouteDto> call, Throwable t) {
                Log.e("Failure saveRoute", t.toString());
            }
        });
        Intent k = new Intent(RouteActivity.this, HomeActivity.class);
        startActivity(k);
    }

    public void getRoute(final String routeId) {
        Call<RouteDto> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).getRoute(routeId);

        call.enqueue(new Callback<RouteDto>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RouteDto> call, Response<RouteDto> response) {
                RouteDto routeDto = response.body();
                MyRoute route = DataConverter.convertRouteData(routeId, routeDto);
                setListsData(route);
            }

            @Override
            public void onFailure(Call<RouteDto> call, Throwable t) {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void joinToRoute() {
        String userId = mUser.getId();

        final ProgressDialog progressDialog = ProgressDialog.show(RouteActivity.this, "", getResources().getString(R.string.saving_message));

        Call<MyUser> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).joinToRoute(routeId, userId, mUser);

        call.enqueue(new Callback<MyUser>() {

            @Override
            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                MyUser myUser = response.body();
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.join_route_message), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<MyUser> call, Throwable t) {
                Toast.makeText(RouteActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    public void deleteRoute() {
        final ProgressDialog progressDialog = ProgressDialog.show(RouteActivity.this, "", getResources().getString(R.string.delete_route_message));

        Call<RouteDto> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).deleteRoute(routeId);

        call.enqueue(new Callback<RouteDto>() {

            @Override
            public void onResponse(Call<RouteDto> call, Response<RouteDto> response) {
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.delete_route_success), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<RouteDto> call, Throwable t) {
                Toast.makeText(RouteActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void disjointFromRoute() {
        String userId = mUser.getId();

        final ProgressDialog progressDialog = ProgressDialog.show(RouteActivity.this, "", getResources().getString(R.string.disjoint_from_route_message));

        Call<MyUser> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).disjointFromRoute(routeId, userId);

        call.enqueue(new Callback<MyUser>() {

            @Override
            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.disjoint_route_success), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<MyUser> call, Throwable t) {
                Toast.makeText(RouteActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}