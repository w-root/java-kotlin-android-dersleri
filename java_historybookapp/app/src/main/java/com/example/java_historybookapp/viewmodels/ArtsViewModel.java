package com.example.java_historybookapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.java_historybookapp.R;
import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ArtsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Art>> arts = new MutableLiveData<ArrayList<Art>>();

    public MutableLiveData<ArrayList<Art>> getArray(){

        return arts;
    }

    public void setArts(ArrayList<Art> artList){
        arts.setValue(artList);
    }

    public ArrayList<Art> getValue(){
        if(arts.getValue() == null){
            return new ArrayList<Art>();
        }
        return arts.getValue();
    }

}
