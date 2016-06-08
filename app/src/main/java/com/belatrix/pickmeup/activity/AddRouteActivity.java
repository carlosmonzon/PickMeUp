package com.belatrix.pickmeup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.belatrix.pickmeup.R;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    private Spinner paymentMethodSpn;

    private Button addRouteBtn;

    private EditText fromEt;

    private EditText toEt;

    private EditText costEt;

    private EditText departureTimeEt;

    private EditText contactEt;

    private EditText streetsEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);
        addRouteBtn = (Button) findViewById(R.id.publish_btn);
        fromEt = (EditText) findViewById(R.id.from_et);
        toEt = (EditText) findViewById(R.id.to_et);
        costEt = (EditText) findViewById(R.id.cost_et);
        departureTimeEt = (EditText) findViewById(R.id.departure_time_et);
        contactEt = (EditText) findViewById(R.id.contact_et);
        streetsEt = (EditText) findViewById(R.id.streets_et);

        // TODO: Make this dynamically?
        // Populate spinner
        List<String> paymentMethods = new ArrayList<String>();
        paymentMethods.add("Contado");
        paymentMethods.add("Crédito");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void validateRoute() {

        boolean hasError = false;

        if (fromEt.getText().toString().trim().equals("")) {
            fromEt.setError(getResources().getString(R.string.add_route_from_empty_error));
            hasError = true;
        }

        if (toEt.getText().toString().trim().equals("")) {
            toEt.setError(getResources().getString(R.string.add_route_to_empty_error));
            hasError = true;
        }

        if (costEt.getText().toString().trim().equals("")) {
            costEt.setError(getResources().getString(R.string.add_route_cost_empty_error));
            hasError = true;
        }

        if (departureTimeEt.getText().toString().trim().equals("")) {
            departureTimeEt.setError(getResources().getString(R.string.add_route_departure_time_empty_error));
            hasError = true;
        }

        if (contactEt.getText().toString().trim().equals("")) {
            contactEt.setError(getResources().getString(R.string.add_route_contact_empty_error));
            hasError = true;
        }

        if (streetsEt.getText().toString().trim().equals("")) {
            streetsEt.setError(getResources().getString(R.string.add_route_streets_empty_error));
            hasError = true;
        }

        // TODO: validate spinner. setError on spinner?

        if (hasError) {
            return;
        }

        // call to save the new route
    }
}
