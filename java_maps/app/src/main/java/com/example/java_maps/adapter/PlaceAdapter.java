package com.example.java_maps.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_maps.databinding.RecyclerRowBinding;
import com.example.java_maps.model.Place;
import com.example.java_maps.model.Singleton;
import com.example.java_maps.view.MainActivity;
import com.example.java_maps.view.MapsActivity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.VHolder> {

    List<Place> places;

    public PlaceAdapter(List<Place> places) {
        this.places = places;
    }

    public class VHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;

        public VHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new VHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recyclerRowBinding.recyclerViewTextView.setText(places.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                Singleton.getInstance().setPlace(places.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return places.size();
    }


}
