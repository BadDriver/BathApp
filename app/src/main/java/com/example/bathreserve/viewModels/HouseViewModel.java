package com.example.bathreserve.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.repositories.HouseRepository;

import java.util.List;

public class HouseViewModel extends AndroidViewModel {
    private HouseRepository houseRepository;
    private  MutableLiveData<String> houseNameLiveData;
    private MutableLiveData<List<String>> usersIdListHouseLiveData;
    private MutableLiveData<List<String>> usersNameListLiveData;

    public HouseViewModel(@NonNull Application application) {
        super(application);
        this.houseRepository = new HouseRepository();
        this.houseNameLiveData = houseRepository.getHouseNameLiveData();
        this.usersIdListHouseLiveData = houseRepository.getUsersIdListHouseLiveData();
        this.usersNameListLiveData = houseRepository.getUsersNameListLiveData();

    }

    public MutableLiveData<String> getHouseNameLiveData() {
        return houseNameLiveData;
    }

    public MutableLiveData<List<String>> getUsersIdListHouseLiveData() {
        return usersIdListHouseLiveData;
    }

    public MutableLiveData<List<String>> getUsersNameListLiveData() {
        return usersNameListLiveData;
    }

    public void addHouse(String name){
        houseRepository.addHouse(name);
    }

    public void joinHouse(String code) {
        houseRepository.joinHouse(code);
    }

    public void getHouseInfo(){
        houseRepository.getHouseInfo();
    }

    public void getUserNameListHouse(){
        houseRepository.getUserNameListHouse();
    }
}
