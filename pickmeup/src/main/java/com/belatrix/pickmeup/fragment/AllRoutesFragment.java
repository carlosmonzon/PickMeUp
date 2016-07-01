package com.belatrix.pickmeup.fragment;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.RouteAdapter;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.UserType;
import com.belatrix.pickmeup.model.Route;
import com.belatrix.pickmeup.model.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AllRoutesFragment extends Fragment {


    private ArrayList<Route> listRoutes;

    private RecyclerView recyclerView;

    private RouteAdapter routeAdapter;


    public AllRoutesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializando las variables que se necesitan en el fragment
        listRoutes = listOfRoutes();
        routeAdapter = new RouteAdapter(listRoutes);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //la instancia view será la contenedora del fragment_all_routes
        View view = inflater.inflate(R.layout.fragment_all_routes, container, false);
        //se usa el recyclerView para el manejo de la data de la lista de rutas
        recyclerView = (RecyclerView) view.findViewById(R.id.listViewRoutes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(routeAdapter);
        routeAdapter.notifyDataSetChanged();
        return view;
    }

    public ArrayList<Route> listOfRoutes() {
        //creacion de mock data
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

        return routes;
    }


}
