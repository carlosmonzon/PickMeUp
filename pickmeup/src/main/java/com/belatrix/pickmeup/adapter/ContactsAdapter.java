package com.belatrix.pickmeup.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.pickmeup.R;
import com.belatrix.pickmeup.activity.ContactDetailsActivity;
import com.belatrix.pickmeup.model.MyUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzavaleta on 09/05/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private List<MyUser> userList;
    private String owner;

    public ContactsAdapter() {
        userList = new ArrayList<>();
        owner= new String();
    }

    public void addUsers(List<MyUser> userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    public void addOwner(String owner){
        this.owner = owner;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyUser user = userList.get(position);
        holder.txtNameContact.setText(user.getFirst_name() + " " + user.getLast_name());
        if(owner.equals(user.getId())){
            holder.txtNameContact.setTextColor(Color.RED);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        } else {
            return userList.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtNameContact;
           private final Context context;

        public MyViewHolder(View view) {
            super(view);

            context = view.getContext();
            txtNameContact = (TextView) view.findViewById(R.id.nameContact);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MyUser currentUser = userList.get(this.getLayoutPosition());
            final Intent intent;
            intent = new Intent(context, ContactDetailsActivity.class);
            Gson gson = new Gson();
            intent.putExtra("userJson", gson.toJson(currentUser));
            context.startActivity(intent);
        }
    }

}
