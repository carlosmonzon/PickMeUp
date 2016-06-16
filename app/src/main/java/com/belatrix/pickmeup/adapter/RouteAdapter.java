package com.belatrix.pickmeup.adapter;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.model.Route;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

    private List<Route> routeList;

    public RouteAdapter(List<Route> routeList) {
        this.routeList = routeList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Route route = routeList.get(position);
        holder.txtDepartureName.setText(route.getDeparture().toString());
        holder.txtDestinationName.setText(route.getDestination().toString());
        holder.txtUserName.setText(route.getRouteOwner().getName());
        holder.txtDepartureTime.setText(route.getDepartureTime());
        holder.txtPlaceAvailable.setText(Integer.toString(route.getPlaceAvailable()));
        holder.imgDestination.setImageResource(R.drawable.sanborja);

        if (route.getPlaceAvailable() == 0) {
            holder.txtPlaceAvailable.setTextColor(Color.RED);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtDepartureName, txtDestinationName, txtUserName, txtDepartureTime,
                txtPlaceAvailable;

        public ImageView imgDestination;

        public MyViewHolder(View view) {
            super(view);

            txtDepartureName = (TextView) view.findViewById(R.id.txtDepartureName);
            txtDestinationName = (TextView) view.findViewById(R.id.txtDestinationName);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            txtDepartureTime = (TextView) view.findViewById(R.id.txtDepartureTime);
            txtPlaceAvailable = (TextView) view.findViewById(R.id.txtPlaceAvailable);
            imgDestination = (ImageView) view.findViewById(R.id.imgDestination);
        }
    }

}
