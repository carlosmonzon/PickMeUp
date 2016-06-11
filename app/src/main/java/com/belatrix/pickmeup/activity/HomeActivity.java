package com.belatrix.pickmeup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.RouteAdapter;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.UserType;
import com.belatrix.pickmeup.model.Route;
import com.belatrix.pickmeup.model.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        User user = new User(1, "Gustavo", "mzavaleta@gmail.com", UserType.OWNER);
        ArrayList<Route> routes = new ArrayList<>();
        routes.add(new Route(0, Departure.BELATRIX_BEGONIAS, Destination.CALLAO, user, "05:30pm", 2));
        routes.add(new Route(1, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, "04:30pm", 3));
        routes.add(new Route(2, Departure.BELATRIX_SAN_ISIDRO, Destination.CHORRILLOS, user, "05:30pm", 0));
        routes.add(new Route(3, Departure.BELATRIX_SAN_ISIDRO, Destination.LINCE, user, "08:00pm", 1));
        routes.add(new Route(4, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, "03:30pm", 2));
        routes.add(new Route(5, Departure.BELATRIX_BEGONIAS, Destination.SAN_BORJA, user, "05:30pm", 2));
        routes.add(new Route(6, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, "04:30pm", 3));
        routes.add(new Route(7, Departure.BELATRIX_SAN_ISIDRO, Destination.CERCADO_DE_LIMA, user, "05:30pm", 0));
        routes.add(new Route(8, Departure.BELATRIX_SAN_ISIDRO, Destination.LINCE, user, "08:00pm", 1));
        routes.add(new Route(9, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, "03:30pm", 2));
        routes.add(new Route(10, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, "05:30pm", 2));
        routes.add(new Route(11, Departure.BELATRIX_BEGONIAS, Destination.CHORRILLOS, user, "04:30pm", 3));
        routes.add(new Route(12, Departure.BELATRIX_SAN_ISIDRO, Destination.SAN_ISIDRO, user, "05:30pm", 0));
        routes.add(new Route(13, Departure.BELATRIX_BEGONIAS, Destination.LINCE, user, "08:00pm", 1));
        routes.add(new Route(14, Departure.BELATRIX_SAN_ISIDRO, Destination.CALLAO, user, "03:30pm", 2));

        RouteAdapter routeAdapter = new RouteAdapter(routes);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listViewRoutes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(routeAdapter);

        routeAdapter.notifyDataSetChanged();
    }

    public void addRouteActivity(View view) {
        Intent intent = new Intent(this, AddRouteActivity.class);
        startActivity(intent);
    }
}
