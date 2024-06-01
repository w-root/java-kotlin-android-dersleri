package com.example.java_twitterclone.Adapters;

import static android.net.Uri.parse;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_twitterclone.Models.Dtos.TweetDto;
import com.example.java_twitterclone.Models.Tweet;
import com.example.java_twitterclone.databinding.TweetRecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTweetsAdapter extends RecyclerView.Adapter<RecyclerTweetsAdapter.VHolder> {

    public class VHolder extends RecyclerView.ViewHolder{
        TweetRecyclerRowBinding recyclerRowBinding;

        public VHolder(TweetRecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }

    List<TweetDto> tweetList;

    public RecyclerTweetsAdapter(List<TweetDto> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TweetRecyclerRowBinding tweetRecyclerRowBinding = TweetRecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new VHolder(tweetRecyclerRowBinding);
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        if(tweetList.get(position).getTweetImages().size() == 0){
            holder.recyclerRowBinding.gridLayout.setVisibility(View.GONE);
        }
        holder.recyclerRowBinding.tvTweetDescription.setText(tweetList.get(position).getTweetDescription());
        holder.recyclerRowBinding.tvNumberOfComments.setText(String.valueOf(tweetList.get(position).getTweetNumberOfComment()));
        holder.recyclerRowBinding.tvNumberOfLikes.setText(String.valueOf(tweetList.get(position).getTweetNumberOfLike()));
        holder.recyclerRowBinding.tvNumberOfRetweet.setText(String.valueOf(tweetList.get(position).getTweetNumberOfRetweet()));
        holder.recyclerRowBinding.tvNumberOfViews.setText(String.valueOf(tweetList.get(position).getTweetNumberOfView()));
        holder.recyclerRowBinding.tvTweetUsername.setText("@" + tweetList.get(position).getUserUsername() + " : 22h");
        holder.recyclerRowBinding.tvTweetName.setText(tweetList.get(position).getUserName());
        Picasso.get().load(tweetList.get(position).getUserImage()).into(holder.recyclerRowBinding.imageView);
        for (String image:tweetList.get(position).getTweetImages()) {
            ImageView imageView = new ImageView(holder.itemView.getContext());
            Picasso.get().load(Uri.parse(image)).into(imageView);
            holder.recyclerRowBinding.gridLayout.addView(imageView,400,200);
        }
    }
}
