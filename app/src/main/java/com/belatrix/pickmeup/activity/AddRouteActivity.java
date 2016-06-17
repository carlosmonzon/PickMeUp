package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.Persistence.RouteFavoriteContract;
import com.belatrix.pickmeup.Persistence.RouteFavoriteDBHelper;
import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.PaymentType;
import com.belatrix.pickmeup.enums.UserType;
import com.belatrix.pickmeup.model.Route;
import com.belatrix.pickmeup.model.User;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    private Spinner paymentMethodSpn;
    private Spinner departureSpn;
    private Spinner destinationSpn;
    private Button addRouteBtn;
    private Button favoriteBtn;
    private TextView fromTil;
    private TextView fromTiet;
    private TextView toTil;
    private TextView toTiet;
    private TextInputLayout costTil;
    private TextInputEditText costTiet;
    private TextInputLayout departureTimeTil;
    private TextInputEditText departureTimeTiet;
    private TextInputLayout contactTil;
    private TextInputEditText contactTiet;
    private TextInputLayout streetsTil;
    private TextInputEditText streetsTiet;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        departureSpn = (Spinner) findViewById(R.id.departure_spn);
        destinationSpn = (Spinner) findViewById(R.id.destination_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);
        favoriteBtn = (Button) findViewById(R.id.favorite_btn);
        fromTil = (TextView) findViewById(R.id.from_til);
        fromTiet = (TextView) departureSpn.getSelectedView();
        toTil = (TextView) findViewById(R.id.to_til);
        toTiet = (TextView) destinationSpn.getSelectedView() ;
        costTil = (TextInputLayout) findViewById(R.id.cost_til);
        costTiet = (TextInputEditText) findViewById(R.id.cost_tiet);
        departureTimeTil = (TextInputLayout) findViewById(R.id.departure_time_til);
        departureTimeTiet = (TextInputEditText) findViewById(R.id.departure_time_tiet);
        contactTil = (TextInputLayout) findViewById(R.id.contact_til);
        contactTiet = (TextInputEditText) findViewById(R.id.contact_tiet);
        streetsTil = (TextInputLayout) findViewById(R.id.streets_til);
        streetsTiet = (TextInputEditText) findViewById(R.id.streets_tiet);

        //set data to Lists
        setLists();


        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    route = new Route(Departure.valueOf(departureSpn.getSelectedItem().toString()),
                            Destination.valueOf(destinationSpn.getSelectedItem().toString()),
                            Double.parseDouble(costTiet.getText().toString()),
                            PaymentType.valueOf("CASH"),
                            new User(1, contactTiet.getText().toString(),
                                    contactTiet.getText().toString() + "@belatrixdf.com", UserType.OWNER),
                            "" + new Date(), streetsTiet.getText().toString());
                }catch (Exception e){

                }
                saveFavoriteRoute(route);
            }
        });

        addRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRoute();

            }
        });
    }

    private void validateRoute() {
        boolean hasError = false;

        fromTil.setError(null);
        toTil.setError(null);
        costTil.setError(null);
        departureTimeTil.setError(null);
        contactTil.setError(null);
        streetsTil.setError(null);

        if (fromTiet.getText().toString().trim().equals("")) {
            fromTil.setError(getResources().getString(R.string.add_route_from_empty_error));
            hasError = true;
        }

        if (toTiet.getText().toString().trim().equals("")) {
            toTil.setError(getResources().getString(R.string.add_route_to_empty_error));
            hasError = true;
        }

        if (costTiet.getText().toString().trim().equals("")) {
            costTil.setError(getResources().getString(R.string.add_route_cost_empty_error));
            hasError = true;
        }

        if (departureTimeTiet.getText().toString().trim().equals("")) {
            departureTimeTil.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        }

        if (contactTiet.getText().toString().trim().equals("")) {
            contactTil.setError(getResources().getString(R.string.add_route_contact_empty_error));
            hasError = true;
        }

        if (streetsTiet.getText().toString().trim().equals("")) {
            streetsTil.setError(getResources().getString(R.string.add_route_streets_empty_error));
            hasError = true;
        }

        if (hasError) {
            Toast.makeText(AddRouteActivity.this, getResources().getString(R.string.add_route_form_error), Toast.LENGTH_SHORT).show();
        }

        // call to save the new route
    }

    public void setLists(){

        // TODO: Make this dynamically?
        // Populate spinner
        List<String> paymentMethods = Arrays.asList(getResources().getString(R.string.cash),
                getResources().getString(R.string.credit));

        // Departure
        List<String> departurePlaces = new ArrayList<>();
        Departure[] departures = Departure.values();
        for (int i=0;i<departures.length;i++){
            departurePlaces.add(departures[i].toString());
        }

        // Destination
        List<String> destinationPlaces = new ArrayList<>();
        Destination[] destinations = Destination.values();
        for (int i=0;i<destinations.length;i++){
            destinationPlaces.add(destinations[i].toString());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);

        ArrayAdapter<String> departureAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departurePlaces);

        ArrayAdapter<String> destinationAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, destinationPlaces);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        paymentMethodSpn.setAdapter(adapter);
        departureSpn.setAdapter(departureAdapter);
        destinationSpn.setAdapter(destinationAdapter);
    }

    public void saveFavoriteRoute(Route route){
        RouteFavoriteDBHelper dbHelper = new RouteFavoriteDBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DEPARTURE, route.getDeparture().toString());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DESTINE, route.getDestination().toString());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_COST, route.getCost());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_PAYMENT_TYPE, route.getPaymentType().toString());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_DEPARTURE_TIME, route.getDepartureTime());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_CONTACT, route.getRouteOwner().getName());
        values.put(RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_ADDRESS, route.getAddressDestination());

        long newRowId = db.insert(
                RouteFavoriteContract.FavoriteEntry.TABLE_NAME,
                RouteFavoriteContract.FavoriteEntry.COLUMN_NAME_ADDRESS,
                values);

        Toast.makeText(getApplicationContext(), "Saved id: "+newRowId, Toast.LENGTH_SHORT).show();

    }


}