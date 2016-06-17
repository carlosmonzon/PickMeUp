package com.belatrix.pickmeup.activity;

import com.belatrix.pickmeup.R;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    private Spinner paymentMethodSpn;

    private Button addRouteBtn;

    private TextInputLayout fromTil;

    private TextInputEditText fromTiet;

    private TextInputLayout toTil;

    private TextInputEditText toTiet;

    private TextInputLayout costTil;

    private TextInputEditText costTiet;

    private TextInputLayout departureTimeTil;

    private TextInputEditText departureTimeTiet;

    private TextInputLayout contactTil;

    private TextInputEditText contactTiet;

    private TextInputLayout streetsTil;

    private TextInputEditText streetsTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);
        fromTil = (TextInputLayout) findViewById(R.id.from_til);
        fromTiet = (TextInputEditText) findViewById(R.id.from_tiet);
        toTil = (TextInputLayout) findViewById(R.id.to_til);
        toTiet = (TextInputEditText) findViewById(R.id.to_tiet);
        costTil = (TextInputLayout) findViewById(R.id.cost_til);
        costTiet = (TextInputEditText) findViewById(R.id.cost_tiet);
        departureTimeTil = (TextInputLayout) findViewById(R.id.departure_time_til);
        departureTimeTiet = (TextInputEditText) findViewById(R.id.departure_time_tiet);
        contactTil = (TextInputLayout) findViewById(R.id.contact_til);
        contactTiet = (TextInputEditText) findViewById(R.id.contact_tiet);
        streetsTil = (TextInputLayout) findViewById(R.id.streets_til);
        streetsTiet = (TextInputEditText) findViewById(R.id.streets_tiet);

        // TODO: Make this dynamically?
        // Populate spinner
        List<String> paymentMethods = new ArrayList<String>();
        paymentMethods.add(getResources().getString(R.string.cash));
        paymentMethods.add(getResources().getString(R.string.credit));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentMethods);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpn.setAdapter(adapter);

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
            Toast.makeText(AddRouteActivity.this, getResources().getString(R.string.add_route_form_error),
                    Toast.LENGTH_SHORT).show();
        }

        // call to save the new route
    }
}