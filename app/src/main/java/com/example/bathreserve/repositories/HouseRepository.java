package com.example.bathreserve.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.House;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<Boolean> ownHouseLiveData;

    public HouseRepository() {
        this.ownHouseLiveData = new MutableLiveData<>();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    /**Adds a house with a choosen name and the logged in user as a part of it */
    public void addHouse(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("houses");
        String key = myRef.push().getKey();
        myRef.child(key).setValue(new House(name, firebaseAuth.getCurrentUser().getUid()));
        myRef = database.getReference("users");
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("house").setValue(key);
    }

}
