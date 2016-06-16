package com.belatrix.pickmeup.fragment;

import com.belatrix.pickmeup.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*
Solo creacion del fragment MyRoutesFragment, este fragment contendrá toda la funcionalidad para la pestaña
de Mis Rutas
 */
public class MyRoutesFragment extends Fragment {

    public MyRoutesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_routes, container, false);
    }


}
