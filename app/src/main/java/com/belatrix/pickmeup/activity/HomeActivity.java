package com.belatrix.pickmeup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.Route;
import com.belatrix.pickmeup.adapter.RouteAdapter;
import com.belatrix.pickmeup.model.User;
import com.belatrix.pickmeup.enums.UserType;

import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User user = new User(1, "Gustavo", "mzavaleta@gmail.com", UserType.OWNER);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(0, Departure.BELATRIX_BEGONIAS, Destination.CALLAO, user, new Date(), 2));
        routes.add(new Route(1, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, new Date(), 3));
        routes.add(new Route(2, Departure.BELATRIX_SAN_ISIDRO, Destination.SAN_ISIDRO, user, new Date(), 0));
        routes.add(new Route(3, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, new Date(), 1));
        routes.add(new Route(4, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, new Date(), 2));

        RouteAdapter routeAdapter = new RouteAdapter(routes);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listViewRoutes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(routeAdapter);

        routeAdapter.notifyDataSetChanged();
    }
}
