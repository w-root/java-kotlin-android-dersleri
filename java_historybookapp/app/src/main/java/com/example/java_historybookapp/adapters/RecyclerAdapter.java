package com.example.java_historybookapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_historybookapp.databinding.RecyclerRowBinding;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.viewmodels.ArtsViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VHolder> {

    List<Art> artList;
    ArtsViewModel viewModel;
    ArrayList<Art> list = new ArrayList<>();

    public RecyclerAdapter(List<Art> artList,ArtsViewModel viewModel) {
        this.artList = artList;
        this.viewModel = viewModel;
    }

    public class VHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;

        public VHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new VHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.recyclerRowBinding.tvRowYear.setText(artList.get(position).getYear());
        holder.recyclerRowBinding.tvRowAuthor4.setText(artList.get(position).getAuthor());
        holder.recyclerRowBinding.tvRowLocation.setText(artList.get(position).getLocation());
        holder.recyclerRowBinding.tvRowName.setText(artList.get(position).getName());

        holder.recyclerRowBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    list.add(artList.get(position));
                } else {
                    list.remove(artList.get(position));
                }
                viewModel.setArts(list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artList.size();
    }
}
