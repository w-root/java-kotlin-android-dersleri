package com.example.java_retrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_retrofit.databinding.RecyclerRowBinding;
import com.example.java_retrofit.model.Crypto;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VHolder> {

    List<Crypto> cryptoList;

    public RecyclerAdapter(List<Crypto> cryptoList) {
        this.cryptoList = cryptoList;
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
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        holder.recyclerRowBinding.textName.setText(cryptoList.get(position).currency);
        holder.recyclerRowBinding.textPrice.setText(cryptoList.get(position).price);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }




}
