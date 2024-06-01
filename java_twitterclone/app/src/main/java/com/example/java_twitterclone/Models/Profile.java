package com.example.java_twitterclone.Models;

import android.net.Uri;

public class Profile {

    private String bio;
    private String location;
    private String username;
    private String userId;
    private Uri image;
    private Uri headerImage;

    public Profile(String bio, String location, String username, String userId,Uri image, Uri headerImage) {
        this.bio = bio;
        this.location = location;
        this.username = username;
        this.image = image;
        this.headerImage = headerImage;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public Uri getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(Uri headerImage) {
        this.headerImage = headerImage;
    }
}
