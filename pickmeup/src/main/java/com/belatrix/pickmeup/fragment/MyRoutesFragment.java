package com.belatrix.pickmeup.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.adapter.RouteAdapter;
import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class MyRoutesFragment extends Fragment {

    private RecyclerView recyclerView;

    private RouteAdapter routeAdapter;

    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializando las variables que se necesitan en el fragment
        routeAdapter = new RouteAdapter();
    }

    public void updateRouteList(List<MyRoute> routes, MyUser mUser) {
        ArrayList<MyRoute> myRoutes = new ArrayList<>();

        for (int i = 0; i < routes.size(); i++) {
            List<MyUser> passengers = routes.get(i).getPassengers();
            for (int k = 0; k < passengers.size(); k++) {
                if (mUser.getId().equals(passengers.get(k).getId())) {
                    myRoutes.add(routes.get(i));
                }
            }
        }
        routeAdapter.addRoutes(myRoutes);
        checkAdapterIsEmpty();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        //la instancia view será la contenedora del fragment_my_routes
        View view = inflater.inflate(R.layout.fragment_my_routes, container, false);
        //se usa el recyclerView para el manejo de la data de la lista de rutas
        recyclerView = (RecyclerView) view.findViewById(R.id.listViewRoutes);
        mTextView = (TextView) view.findViewById(R.id.empty_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(routeAdapter);
        return view;
    }

    private void checkAdapterIsEmpty () {
        if (routeAdapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
        }
    }

}
