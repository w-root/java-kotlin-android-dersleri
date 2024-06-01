package com.example.java_twitterclone.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedViewModel extends ViewModel {

    private MutableLiveData<Boolean> checkVisibility = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCheckVisibility() {
        return checkVisibility;
    }

    public void setCheckVisibility(boolean checkVisible) {
        checkVisibility.setValue(checkVisible);
    }


}
