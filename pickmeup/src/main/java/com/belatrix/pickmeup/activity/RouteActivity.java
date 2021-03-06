package com.belatrix.pickmeup.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.ContactsAdapter;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;
import com.belatrix.pickmeup.fragment.DatePickerFragment;
import com.belatrix.pickmeup.fragment.TimePickerFragment;
import com.belatrix.pickmeup.model.FirebaseResponse;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.RouteDto;
import com.belatrix.pickmeup.model.TimePicked;
import com.belatrix.pickmeup.rest.PickMeUpFirebaseClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;
import com.belatrix.pickmeup.util.SharedPreferenceManager;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteActivity extends AppCompatActivity
        implements TimePickerFragment.TimeSelected, DatePickerFragment.DateSelected {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String routeId;

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

    private TextInputLayout streetsTil;

    private TextInputEditText streetsTiet;

    private TextInputLayout passengerMaxCapacityTil;

    private TextInputEditText passengerMaxCapacityTiet;

    private MyUser mUser;

    private TimePicked timePicked = new TimePicked();

    private RecyclerView recyclerView;

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


        streetsTil = (TextInputLayout) findViewById(R.id.streets_til);
        streetsTiet = (TextInputEditText) findViewById(R.id.streets_tiet);

        passengerMaxCapacityTil = (TextInputLayout) findViewById(R.id.passenger_max_capacity_til);
        passengerMaxCapacityTiet = (TextInputEditText) findViewById(R.id.passenger_max_capacity_tiet);

       // contactTiet.setEnabled(false);
        String FullName = mUser.getFirst_name() + " " + mUser.getLast_name();
        //contactTiet.setText(FullName);
        //set data to Lists

        Gson gson = new Gson();
        // return gson.fromJson(sharedPref.getString("myUser", ""), MyUser.class);
        Intent intent = getIntent();
        final MyRoute currentRoute = gson.fromJson(intent.getStringExtra("routeJson"), MyRoute.class);

        if (null != currentRoute) {
            setListsData(currentRoute);
            routeId = currentRoute.getId();

            ContactsAdapter contactsAdapter = new ContactsAdapter();

            contactsAdapter.addUsers(currentRoute.getPassengers());
            contactsAdapter.addOwner(currentRoute.getOwner().getId());
            recyclerView = (RecyclerView) findViewById(R.id.listContacts);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(contactsAdapter);
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
                joinToRoute(currentRoute.getId());
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

        fromTil.setError(null);
        toTil.setError(null);
        costTil.setError(null);
        departureTimeTil.setError(null);
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

            newRoute.setOwner(mUser);

        if (departureTimeTiet.getText().toString().trim().equals("")) {
            departureTimeTil.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        } else {
            newRoute.setDepartureTime(Long.toString(timePicked.timePickedToMiliseconds()));
        }

        if (passengerMaxCapacityTiet.getText().length() == 0) {
            passengerMaxCapacityTil
                    .setError(getResources().getString(R.string.add_route_passengers_max_capacity_empty_error));
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

        getSupportActionBar().setTitle("Route Detail");
        populateSpinner(paymentMethodSpn, PaymentType.getList(), route.getPaymentType().toString());
        populateSpinner(departureSpn, Departure.getList(), route.getDeparture().toString());
        populateSpinner(destinationSpn, Destination.getList(), route.getDestination().toString());
        costTiet.setText(route.getCost().toString());

        Long dateDeparture = Long.parseLong(route.getDepartureTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateDeparture);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");

        departureTimeTiet.setText(sdf.format(calendar.getTime()).toString());

        streetsTiet.setText(route.getAddressDestination());
        passengerMaxCapacityTiet.setText(String.valueOf(route.getPlaceAvailable() -
                (route.getPassengers() != null ? route.getPassengers().size() : 0)));
        addRouteBtn.setVisibility(View.GONE);

        paymentMethodSpn.setFocusable(false);
        departureSpn.setFocusable(false);
        destinationSpn.setFocusable(false);
        costTiet.setFocusable(false);
        departureTimeTiet.setFocusable(false);
        streetsTiet.setFocusable(false);
        passengerMaxCapacityTiet.setFocusable(false);
        addRouteBtn.setFocusable(false);

        if (mUser == null) {
            return;
        }

        int takenPlaces = route.getPassengers() != null ? route.getPassengers().size() : 0;
        int totalPlaces = route.getPlaceAvailable() - takenPlaces;

        if (totalPlaces <= 0) {
            joinRouteBtn.setVisibility(View.GONE);
        }

        if (route.getOwner().getId().equals(mUser.getId())) {
            deleteRouteBtn.setVisibility(View.VISIBLE);
            joinRouteBtn.setVisibility(View.GONE);
            disjointRouteBtn.setVisibility(View.GONE);
        } else {
            try {
                for (MyUser user : route.getPassengers()) {
                    if (user.getId().equals(mUser.getId())) {
                        disjointRouteBtn.setVisibility(View.VISIBLE);
                        joinRouteBtn.setVisibility(View.GONE);
                        break;
                    }
                }
            } catch (Exception e) {

            }

        }
    }

    public void saveRoute(MyRoute route) {
        Call<FirebaseResponse> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).registerRoute(route);

        call.enqueue(new Callback<FirebaseResponse>() {
            @Override
            public void onResponse(Call<FirebaseResponse> call, Response<FirebaseResponse> response) {
                try {
                    Toast.makeText(RouteActivity.this, "Route has been successfully created",
                            Toast.LENGTH_SHORT).show();
                    FirebaseResponse firebaseResponse = response.body();

                    routeId = firebaseResponse.getName();
                    joinToRoute(routeId);
                    Intent k = new Intent(RouteActivity.this, HomeActivity.class);
                    startActivity(k);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<FirebaseResponse> call, Throwable t) {
                Log.e("Failure saveRoute", t.toString());
            }
        });
        Intent k = new Intent(RouteActivity.this, HomeActivity.class);
        startActivity(k);
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
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Long dateDeparture = timePicked.timePickedToMiliseconds();

        departureTimeTiet.setText(sdf.format(dateDeparture).toString());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void joinToRoute(String routeId) {
        String userId = mUser.getId();

        final ProgressDialog progressDialog = ProgressDialog
                .show(RouteActivity.this, "", getResources().getString(R.string.saving_message));

        Call<MyUser> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).joinToRoute(routeId, userId, mUser);

        call.enqueue(new Callback<MyUser>() {

            @Override
            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                MyUser myUser = response.body();
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.join_route_message), Toast.LENGTH_SHORT)
                        .show();
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
        final ProgressDialog progressDialog = ProgressDialog
                .show(RouteActivity.this, "", getResources().getString(R.string.delete_route_message));

        Call<RouteDto> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).deleteRoute(routeId);

        call.enqueue(new Callback<RouteDto>() {

            @Override
            public void onResponse(Call<RouteDto> call, Response<RouteDto> response) {
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.delete_route_success),
                        Toast.LENGTH_SHORT).show();
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

        final ProgressDialog progressDialog = ProgressDialog
                .show(RouteActivity.this, "", getResources().getString(R.string.disjoint_from_route_message));

        Call<MyUser> call = ServiceGenerator.createService(PickMeUpFirebaseClient.class).disjointFromRoute(routeId, userId);

        call.enqueue(new Callback<MyUser>() {

            @Override
            public void onResponse(Call<MyUser> call, Response<MyUser> response) {
                Toast.makeText(RouteActivity.this, getResources().getString(R.string.disjoint_route_success),
                        Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}