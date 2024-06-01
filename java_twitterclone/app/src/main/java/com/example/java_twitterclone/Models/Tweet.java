package com.example.java_twitterclone.Models;


import android.net.Uri;

import java.util.ArrayList;

public class Tweet {

    private String description;
    private String date;
    private String userId;
    private long numberOfLike;
    private long numberOfComment;
    private long numberOfRetweet;
    private long numberOfView;
    private ArrayList<String> tweetImages;



    public Tweet(String description, String date, String userId, ArrayList<String> tweetImages,long numberOfLike, long numberOfComment, long numberOfRetweet, long numberOfView) {
        this.description = description;
        this.date = date;
        this.userId = userId;
        this.numberOfLike = numberOfLike;
        this.numberOfComment = numberOfComment;
        this.numberOfRetweet = numberOfRetweet;
        this.numberOfView = numberOfView;
        this.tweetImages = tweetImages;
    }
    public ArrayList<String> getTweetImages() {
        return tweetImages;
    }

    public void setTweetImages(ArrayList<String> tweetImages) {
        this.tweetImages = tweetImages;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getNumberOfLike() {
        return numberOfLike;
    }

    public void setNumberOfLike(long numberOfLike) {
        this.numberOfLike = numberOfLike;
    }

    public long getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(long numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    public long getNumberOfRetweet() {
        return numberOfRetweet;
    }

    public void setNumberOfRetweet(long numberOfRetweet) {
        this.numberOfRetweet = numberOfRetweet;
    }

    public long getNumberOfView() {
        return numberOfView;
    }

    public void setNumberOfView(long numberOfView) {
        this.numberOfView = numberOfView;
    }


}
