package com.example.java_instagramclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_instagramclone.databinding.RecyclerRowBinding;
import com.example.java_instagramclone.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VHolder> {

    List<Post> posts;

    public RecyclerAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public class VHolder extends  RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding;
        public VHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
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
        holder.recyclerRowBinding.tvComment.setText(posts.get(position).comment);
        holder.recyclerRowBinding.tvUseremail.setText(posts.get(position).email);
        Picasso.get().load(posts.get(position).url).into(holder.recyclerRowBinding.imageView);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



}
