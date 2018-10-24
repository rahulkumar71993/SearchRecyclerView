package com.example.rahul.searchrecyclerview;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 6/6/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Dat> names;
    Context context;

    public CustomAdapter(Context c, List<Dat> names) {
        this.names = names;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textViewName.setText(names.get(position).getName());

        holder.textPhone.setText(names.get(position).getPhone());
        Glide.with(context)
                .load(names.get(position).getImg())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+names.get(position).getName()+names.get(position).getPhone(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textPhone;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            img = itemView.findViewById(R.id.img);
            textPhone = (TextView) itemView.findViewById(R.id.textPhone);
        }
    }

    public void filterList(ArrayList<Dat> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}