package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.repositories.HouseRepository;

import java.util.List;

public class HouseViewModel extends AndroidViewModel {
    private HouseRepository houseRepository;
    private MutableLiveData<String> houseNameLiveData;
    private MutableLiveData<List<String>> usersNameListLiveData;
    private MutableLiveData<List<String>> allUsersEmailLiveData;

    public HouseViewModel(@NonNull Application application) {
        super(application);
        this.houseRepository = new HouseRepository();
        this.houseNameLiveData = houseRepository.getHouseNameLiveData();
        this.usersNameListLiveData = houseRepository.getUsersNameListLiveData();
        this.allUsersEmailLiveData = houseRepository.getAllUsersEmailLiveData();
    }

    public MutableLiveData<String> getHouseNameLiveData() {
        return houseNameLiveData;
    }

    public MutableLiveData<List<String>> getUsersNameListLiveData() {
        return usersNameListLiveData;
    }

    public MutableLiveData<List<String>> getAllUsersEmailLiveData() {
        return allUsersEmailLiveData;
    }

    public void getAllUsers(){
        houseRepository.getAllUsersEmail();
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

    public void changeHouseName(String newName){
        houseRepository.changeHouseName(newName);
    }

    public void addUser(String email){
        houseRepository.addUser(email);
    }
}
