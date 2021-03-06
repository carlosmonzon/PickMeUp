package com.belatrix.pickmeup.adapter;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.activity.RouteActivity;
import com.belatrix.pickmeup.model.MyRoute;
import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

    private List<MyRoute> routeList;

    public RouteAdapter() {
        routeList = new ArrayList<>();
    }

    public void addRoutes(List<MyRoute> routeList) {
        this.routeList.clear();
        this.routeList.addAll(routeList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyRoute route = routeList.get(position);
        holder.txtDepartureName.setText(route.getDeparture().toString());
        holder.txtDestinationName.setText(route.getDestination().toString());
        String FullName = route.getOwner().getFirst_name() + " " + route.getOwner().getLast_name();
        holder.txtUserName.setText(FullName);//needs work

        Long dateMilliseconds = Long.parseLong(route.getDepartureTime());

        Date newDate = new Date(dateMilliseconds);

        holder.txtDepartureTime.setText(newDate.toString());
        holder.txtPrice.setText("S/ " + (route.getCost() == null ? "0.00" : route.getCost()));

        int takenPlaces = route.getPassengers() != null ? route.getPassengers().size() : 0;
        int totalPlaces = route.getPlaceAvailable() - takenPlaces;

        if (totalPlaces < 0) {
            totalPlaces = 0;
        }

        holder.txtPlaceAvailable.setText(String.valueOf(totalPlaces));

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
        if (routeList == null) {
            return 0;
        } else {
            return routeList.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtDepartureName, txtDestinationName, txtUserName, txtDepartureTime,
                txtPlaceAvailable, txtPrice;


        private final Context context;

        public MyViewHolder(View view) {
            super(view);

            context = view.getContext();

            txtDepartureName = (TextView) view.findViewById(R.id.txtDepartureName);
            txtDestinationName = (TextView) view.findViewById(R.id.txtDestinationName);
            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            txtDepartureTime = (TextView) view.findViewById(R.id.txtDepartureTime);
            txtPlaceAvailable = (TextView) view.findViewById(R.id.txtPlaceAvailable);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MyRoute currentRoute = routeList.get(this.getLayoutPosition());
            final Intent intent;
            intent = new Intent(context, RouteActivity.class);

            Gson gson = new Gson();
            intent.putExtra("routeJson", gson.toJson(currentRoute));
            context.startActivity(intent);


        }
    }

}
