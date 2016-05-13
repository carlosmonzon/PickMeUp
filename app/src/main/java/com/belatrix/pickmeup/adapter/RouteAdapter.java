package com.belatrix.pickmeup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.Route;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class RouteAdapter extends ArrayAdapter<Route>{

    public RouteAdapter(Context context, ArrayList<Route> routes) {
        super(context, 0, routes);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        final Route item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route_item, parent, false);
        }

        TextView txtDepartureName = (TextView) convertView.findViewById(R.id.txtDepartureName);
        TextView txtDestinationName = (TextView) convertView.findViewById(R.id.txtDestinationName);
        TextView txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
        TextView txtDepartureTime = (TextView) convertView.findViewById(R.id.txtDepartureTime);
        TextView txtPlaceAvailable = (TextView) convertView.findViewById(R.id.txtPlaceAvailable);

        txtDepartureName.setText(item.getDeparture().toString());
        txtDestinationName.setText(item.getDestination().toString());
        txtUserName.setText(item.getRouteOwner().getName());
        txtDepartureTime.setText(new Date().toString());
        txtPlaceAvailable.setText(Integer.toString(item.getPlaceAvailable()));
        return convertView;
    }

}
