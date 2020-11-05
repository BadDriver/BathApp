package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.House;
import com.example.bathreserve.repositories.AccountRepository;
import com.example.bathreserve.repositories.HouseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HouseViewModel extends AndroidViewModel {
    private HouseRepository houseRepository;

    public HouseViewModel(@NonNull Application application) {
        super(application);
        this.houseRepository = new HouseRepository();
    }

    public void addHouse(String name){
        houseRepository.addHouse(name);
    }

    public void joinHouse(String code) {
        houseRepository.joinHouse(code);
    }
}
