package com.example.java_currencyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_currencyapp.databinding.RecyclerRowBinding;


import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VHolder> {

    List<Currency> currencyList;

    public RecyclerAdapter(List<Currency> currencyList) {
        this.currencyList = currencyList;
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
        holder.recyclerRowBinding.tvCurrencyName.setText(currencyList.get(position).currencyName);
        holder.recyclerRowBinding.tvBuying.setText(String.valueOf(currencyList.get(position).buying));
        holder.recyclerRowBinding.tvSelling.setText(String.valueOf(currencyList.get(position).selling));
        holder.recyclerRowBinding.tvChange.setText(String.valueOf(currencyList.get(position).change));
        holder.recyclerRowBinding.tvType.setText(currencyList.get(position).type);

        if(currencyList.get(position).change < 0){
            holder.recyclerRowBinding.tvChange.setTextColor(Color.RED);
        } else {
            holder.recyclerRowBinding.tvChange.setTextColor(Color.GREEN);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(),MainActivity3.class);
                Singleton.getInstance().setCurrency(currencyList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }


}
