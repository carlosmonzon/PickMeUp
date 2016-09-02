package com.belatrix.pickmeup.fragment;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.RouteAdapter;
import com.belatrix.pickmeup.enums.Departure;
import com.belatrix.pickmeup.enums.Destination;
import com.belatrix.pickmeup.enums.UserType;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.Route;
import com.belatrix.pickmeup.model.User;
import com.belatrix.pickmeup.rest.PickMeUpClient;
import com.belatrix.pickmeup.rest.ServiceGenerator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class AllRoutesFragment extends Fragment {


    private ArrayList<MyRoute> listRoutes;
    private RecyclerView recyclerView;
    private RouteAdapter routeAdapter;

    @SuppressLint("ValidFragment")
    public AllRoutesFragment(List<MyRoute> routes) {
        this.listRoutes = new ArrayList<>(routes);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializando las variables que se necesitan en el fragment
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
}
