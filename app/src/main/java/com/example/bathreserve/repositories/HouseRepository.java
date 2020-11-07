package com.example.bathreserve.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.bathreserve.models.House;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HouseRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> houseNameLiveData;
    private MutableLiveData<List<String>> usersListHouseLiveData;

    public HouseRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.houseNameLiveData = new MutableLiveData<>();
        this.usersListHouseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getHouseNameLiveData() {
        return houseNameLiveData;
    }

    public MutableLiveData<List<String>> getUsersListHouseLiveData() {
        return usersListHouseLiveData;
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
        DatabaseReference databaseReference = database.getReference("houses");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshotUsers : dataSnapshot.child("users").getChildren()){
                        if(dataSnapshotUsers.getValue().toString().equals(firebaseAuth.getCurrentUser().getUid())){
                            houseNameLiveData.postValue(dataSnapshot.child("name").getValue().toString());
                            break;
                        }
                    }
                    if(houseNameLiveData != null){
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getUserListHouse(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("house");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> userListTemp = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("house").equals(houseId)){
                        userListTemp.add(dataSnapshot.child("name").getValue().toString());
                    }
                }
                usersListHouseLiveData.postValue(userListTemp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }
}
