package com.belatrix.pickmeup.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.belatrix.pickmeup.R;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    private Spinner paymentMethodSpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentMethodSpn = (Spinner) findViewById(R.id.payment_method_spn);

        // TODO: Make this dynamically?
        // Populate spinner
        List<String> paymentMethods = new ArrayList<String>();
        paymentMethods.add("Contado");
        paymentMethods.add("Crédito");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentMethods);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpn.setAdapter(adapter);
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
}
