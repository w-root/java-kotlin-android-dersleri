package com.example.java_tabactivity.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();

    public MutableLiveData<String> getName(){
        return name;
    }

    public void setName(String n){
        name.setValue(n);
    }
}