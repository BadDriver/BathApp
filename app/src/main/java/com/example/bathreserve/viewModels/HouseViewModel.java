package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.bathreserve.models.House;
import com.example.bathreserve.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HouseViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    public HouseViewModel(@NonNull Application application) {
        super(application);
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void addHouse(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("houses");
        myRef.push().setValue(new House(name, firebaseAuth.getCurrentUser().getUid()));

    }
}
