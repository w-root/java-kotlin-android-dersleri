package com.example.java_twitterclone.ViewModels;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_twitterclone.Models.Profile;

public class ProfileViewModel {

    private String bio;
    private String location;
    private String username;
    private String name;
    private Uri imageUri;
    private Uri headerImageUri;

    public static ProfileViewModel profileViewModel;

    public static ProfileViewModel getInstance(){
        if(profileViewModel == null){
            profileViewModel = new ProfileViewModel();
        }
        return profileViewModel;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getHeaderImageUri() {
        return headerImageUri;
    }

    public void setHeaderImageUri(Uri headerImageUri) {
        this.headerImageUri = headerImageUri;
    }
}
