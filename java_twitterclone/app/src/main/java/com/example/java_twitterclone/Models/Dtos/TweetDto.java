package com.example.java_twitterclone.Models.Dtos;

import android.net.Uri;

import java.util.ArrayList;

public class TweetDto {

    private String id;
    private String userUsername;
    private String userName;
    private String date;
    private String tweetDescription;
    private Uri userImage;
    private long tweetNumberOfLike;
    private long tweetNumberOfComment;
    private ArrayList<String> tweetImages;

    public ArrayList<String> getTweetImages() {
        return tweetImages;
    }

    public void setTweetImages(ArrayList<String> tweetImages) {
        this.tweetImages = tweetImages;
    }

    public Uri getUserImage() {
        return userImage;
    }

    public void setUserImage(Uri userImage) {
        this.userImage = userImage;
    }

    public TweetDto(String id, String userUsername, String userName, Uri userImage,
                    String date, String tweetDescription, ArrayList<String> tweetImages,long tweetNumberOfLike, long tweetNumberOfComment, long tweetNumberOfRetweet, long tweetNumberOfView) {
        this.id = id;
        this.userUsername = userUsername;
        this.userName = userName;
        this.date = date;
        this.userImage = userImage;
        this.tweetDescription = tweetDescription;
        this.tweetNumberOfLike = tweetNumberOfLike;
        this.tweetNumberOfComment = tweetNumberOfComment;
        this.tweetNumberOfRetweet = tweetNumberOfRetweet;
        this.tweetNumberOfView = tweetNumberOfView;
        this.tweetImages = tweetImages;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTweetDescription() {
        return tweetDescription;
    }

    public void setTweetDescription(String tweetDescription) {
        this.tweetDescription = tweetDescription;
    }

    public long getTweetNumberOfLike() {
        return tweetNumberOfLike;
    }

    public void setTweetNumberOfLike(long tweetNumberOfLike) {
        this.tweetNumberOfLike = tweetNumberOfLike;
    }

    public long getTweetNumberOfComment() {
        return tweetNumberOfComment;
    }

    public void setTweetNumberOfComment(long tweetNumberOfComment) {
        this.tweetNumberOfComment = tweetNumberOfComment;
    }

    public long getTweetNumberOfRetweet() {
        return tweetNumberOfRetweet;
    }

    public void setTweetNumberOfRetweet(long tweetNumberOfRetweet) {
        this.tweetNumberOfRetweet = tweetNumberOfRetweet;
    }

    public long getTweetNumberOfView() {
        return tweetNumberOfView;
    }

    public void setTweetNumberOfView(long tweetNumberOfView) {
        this.tweetNumberOfView = tweetNumberOfView;
    }

    private long tweetNumberOfRetweet;
    private long tweetNumberOfView;

}
