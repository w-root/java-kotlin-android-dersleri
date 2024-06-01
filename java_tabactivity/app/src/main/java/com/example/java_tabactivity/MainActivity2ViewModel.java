package com.example.java_tabactivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivity2ViewModel extends ViewModel {

    MutableLiveData<String> str = new MutableLiveData<>();

    public MutableLiveData<String> getValue(){
        return str;
    }

    public void setStr(String strr){
        str.setValue(strr);
    }

}
