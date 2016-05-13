package com.belatrix.pickmeup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    public String text = "Helloo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User user = new User(1, "Gustavo", "mzavaleta@gmail.com", UserType.OWNER);
        ArrayList<Route> routes = new ArrayList<>();


        RouteAdapter routeAdapter = new RouteAdapter(getApplicationContext(), routes);

        ListView listView = (ListView) findViewById(R.id.listViewRoutes);
        assert listView != null;
        listView.setAdapter(routeAdapter);

        //temporary location for instance List View
        routes.add(new Route(0, Departure.BELATRIX_BEGONIAS, Destination.CALLAO, user, new Date(), 2));
        routes.add(new Route(1, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, new Date(), 3));
        routes.add(new Route(2, Departure.BELATRIX_SAN_ISIDRO, Destination.SAN_ISIDRO, user, new Date(), 0));
        routes.add(new Route(3, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, new Date(), 1));
        routes.add(new Route(4, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, new Date(), 2));

        routeAdapter.addAll(routes);
        routeAdapter.notifyDataSetChanged();

       // new setListTask().execute();
    }
}
