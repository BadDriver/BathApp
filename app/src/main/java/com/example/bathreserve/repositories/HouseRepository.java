package com.example.bathreserve.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.House;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HouseRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> houseNameLiveData;

    public HouseRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        houseNameLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getHouseNameLiveData() {
        return houseNameLiveData;
    }

    /**Adds a house with a choosen name and the logged in user as a part of it */
    public void addHouse(String name){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("houses");
        String key = myRef.push().getKey();
        //add a house with a random name
        myRef.child(key).setValue(new House(name, firebaseAuth.getCurrentUser().getUid()));
        //add the user to user list of the house
        myRef = database.getReference("users");
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("house").setValue(key);
    }

    /**Join a house by the house id, then it will also add that house id to the user's house code */
    public void joinHouse(String code){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("houses");
        ArrayList<String> tempList = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(code)){
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.child("users").getChildren()){
                            tempList.add(dataSnapshot1.getValue().toString());
                        }
                        tempList.add(firebaseAuth.getCurrentUser().getUid());
                    }
                }
                myRef.child(code).child("users").setValue(tempList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);
        DatabaseReference userRef = database.getReference("users");
        userRef.child(firebaseAuth.getCurrentUser().getUid()).child("house").setValue(code);
    }

    public void getHouseName(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String houseId;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                        houseId = dataSnapshot.child("house").getValue().toString();
                        DatabaseReference houseRef = database.getReference("houses");
                        String finalHouseId = houseId;
                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("pla", finalHouseId);
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    if(dataSnapshot.getKey().equals(finalHouseId)){
                                        houseNameLiveData.postValue(dataSnapshot.child("name").getValue().toString());
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        houseRef.addListenerForSingleValueEvent(valueEventListener);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);
    }
}
