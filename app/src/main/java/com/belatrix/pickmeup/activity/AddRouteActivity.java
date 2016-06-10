package com.belatrix.pickmeup.activity;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.belatrix.pickmeup.R;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    private Spinner paymentMethodSpn;

    private Button addRouteBtn;

    private TextInputEditText fromTiet;

    private TextInputEditText toTiet;

    private TextInputEditText costTiet;

    private TextInputEditText departureTimeTiet;

    private TextInputEditText contactTiet;

    private TextInputEditText streetsTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);
        fromTiet = (TextInputEditText) findViewById(R.id.from_tiet);
        toTiet = (TextInputEditText) findViewById(R.id.to_tiet);
        costTiet = (TextInputEditText) findViewById(R.id.cost_tiet);
        departureTimeTiet = (TextInputEditText) findViewById(R.id.departure_time_tiet);
        contactTiet = (TextInputEditText) findViewById(R.id.contact_tiet);
        streetsTiet = (TextInputEditText) findViewById(R.id.streets_tiet);

        // TODO: Make this dynamically?
        // Populate spinner
        List<String> paymentMethods = new ArrayList<String>();
        paymentMethods.add(getResources().getString(R.string.payment_method));
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

        if (fromTiet.getText().toString().trim().equals("")) {
            fromTiet.setError(getResources().getString(R.string.add_route_from_empty_error));
            hasError = true;
        }

        if (toTiet.getText().toString().trim().equals("")) {
            toTiet.setError(getResources().getString(R.string.add_route_to_empty_error));
            hasError = true;
        }

        if (costTiet.getText().toString().trim().equals("")) {
            costTiet.setError(getResources().getString(R.string.add_route_cost_empty_error));
            hasError = true;
        }

        if (paymentMethodSpn.getSelectedItem().toString().equals(getResources().getString(R.string.payment_method))) {
            TextView errorText = (TextView) paymentMethodSpn.getSelectedView();
            errorText.setError("x");
            errorText.setTextColor(Color.RED);
            errorText.setText(getResources().getString(R.string.add_route_payment_method_empty_error));
            hasError = true;
        }

        if (departureTimeTiet.getText().toString().trim().equals("")) {
            departureTimeTiet.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        }

        if (contactTiet.getText().toString().trim().equals("")) {
            contactTiet.setError(getResources().getString(R.string.add_route_contact_empty_error));
            hasError = true;
        }

        if (streetsTiet.getText().toString().trim().equals("")) {
            streetsTiet.setError(getResources().getString(R.string.add_route_streets_empty_error));
            hasError = true;
        }

        // TODO: validate spinner. setError on spinner?

        if (hasError) {
            Toast.makeText(AddRouteActivity.this, getResources().getString(R.string.add_route_form_error), Toast.LENGTH_SHORT).show();
            return;
        }

        // call to save the new route
    }
}
