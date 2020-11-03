package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.House;
import com.example.bathreserve.repositories.HouseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    private HouseRepository houseRepository;
    private MutableLiveData<Boolean> ownHouseLiveData;
    public HouseViewModel(@NonNull Application application) {
        super(application);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.houseRepository = new HouseRepository(application);
        ownHouseLiveData = houseRepository.getOwnHouseLiveData();
        this.houseRepository.checkUserHouse();
    }

    public void addHouse(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("houses");
        String key = myRef.push().getKey();
        myRef.child(key).setValue(new House(name, firebaseAuth.getCurrentUser().getUid()));

        myRef = database.getReference("users");
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("house").setValue(key);
    }

    public MutableLiveData<Boolean> getOwnHouseLiveData() {
        return ownHouseLiveData;
    }
}
