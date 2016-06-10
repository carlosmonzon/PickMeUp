package com.belatrix.pickmeup.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.pickmeup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyRoutes_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyRoutes_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRoutes_Fragment extends Fragment {
    public MyRoutes_Fragment() {
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
