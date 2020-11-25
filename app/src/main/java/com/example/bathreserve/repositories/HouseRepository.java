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
import java.util.List;

public class HouseRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<String> houseNameLiveData;
    private ArrayList<String> usersListId;
    private MutableLiveData<List<String>> usersNameListLiveData;
    private MutableLiveData<List<String>> allUsersEmailLiveData;
    private String houseId;
    private String userId;

    public HouseRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.houseNameLiveData = new MutableLiveData<>();
        this.usersNameListLiveData = new MutableLiveData<>();
        this.allUsersEmailLiveData = new MutableLiveData<>();
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

    /**Gets the house name and the list of user id, which will be used to get their name */
    public void getHouseInfo(){
        usersListId = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("houses");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshotUsers : dataSnapshot.child("users").getChildren()){
                        usersListId.add(dataSnapshotUsers.getValue().toString());
                    }
                    if(usersListId.contains(firebaseAuth.getCurrentUser().getUid())){
                        houseNameLiveData.postValue(dataSnapshot.child("name").getValue().toString());
                        houseId = dataSnapshot.getKey();
                        DatabaseReference databaseReferenceUserNames = database.getReference("users");
                        List<String> tempList = new ArrayList<>();
                        ValueEventListener valueEventListenerUserName = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    if(usersListId.contains(dataSnapshot.getKey())){
                                        tempList.add(dataSnapshot.child("name").getValue().toString());
                                    }
                                }
                                usersNameListLiveData.postValue(tempList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        databaseReferenceUserNames.addListenerForSingleValueEvent(valueEventListenerUserName);
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

    /**Because this method will be always called after getHouseInfo, the  string houseId will be always filled with the fetched house id */
    public void changeHouseName(String newName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceHouse = database.getReference("houses");
        databaseReferenceHouse.child(houseId).child("name").setValue(newName);
        houseNameLiveData.postValue(newName);
    }

    public void addUser(String email){
        List<String> tempList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue().toString().equals(email)){
                        userId = dataSnapshot.getKey();
                        databaseReference.child(dataSnapshot.getKey()).child("house").setValue(houseId);
                        DatabaseReference databaseReferenceHouse = database.getReference("houses");
                        ValueEventListener valueEventListenerHouse = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshotHouse : snapshot.getChildren()){
                                    if(dataSnapshotHouse.getKey().equals(houseId)){
                                        for(DataSnapshot usersToAdd : dataSnapshotHouse.child("users").getChildren()){
                                            tempList.add(usersToAdd.getValue().toString());
                                        }
                                        tempList.add(userId);
                                        break;
                                    }
                                }
                                databaseReferenceHouse.child(houseId).child("users").setValue(tempList);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };databaseReferenceHouse.addListenerForSingleValueEvent(valueEventListenerHouse);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getAllUsersEmail(){
        ArrayList<String> temp = new ArrayList();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    temp.add(dataSnapshot.child("email").getValue().toString());
                }
                allUsersEmailLiveData.postValue(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void leaveHouse(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //remove the user from user list
        DatabaseReference databaseReferenceUsers = database.getReference("users");
        ValueEventListener valueEventListenerUsers = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                        databaseReferenceUsers.child(dataSnapshot.getKey()).child("house").removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };databaseReferenceUsers.addListenerForSingleValueEvent(valueEventListenerUsers);
        //remove the user from the house user list
        List<String> tempList = new ArrayList<>();
        DatabaseReference databaseReferenceHouse = database.getReference("houses");
        ValueEventListener valueEventListenerHouse = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshotHouse : snapshot.getChildren()){
                    if(dataSnapshotHouse.getKey().equals(houseId)){
                        for(DataSnapshot usersToAdd : dataSnapshotHouse.child("users").getChildren()){
                            if(!usersToAdd.getValue().toString().equals(firebaseAuth.getCurrentUser().getUid())){
                                tempList.add(usersToAdd.getValue().toString());
                            }
                        }
                        break;
                    }
                }
                databaseReferenceHouse.child(houseId).child("users").setValue(tempList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };databaseReferenceHouse.addListenerForSingleValueEvent(valueEventListenerHouse);
        //remove users reservations
        ArrayList<String> keysToRemove = new ArrayList<>();
        DatabaseReference databaseReferenceReservations = database.getReference("reservations");
        ValueEventListener valueEventListenerReservations = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(houseId)){
                        for(DataSnapshot dataSnapshotReservations : dataSnapshot.getChildren()){
                            if(dataSnapshotReservations.child("userId").getValue().equals(firebaseAuth.getCurrentUser().getUid())){
                                keysToRemove.add(dataSnapshotReservations.getKey());
                            }
                        }
                        break;
                    }
                }
                for(String s : keysToRemove){
                    databaseReferenceReservations.child(houseId).child(s).removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };databaseReferenceReservations.addListenerForSingleValueEvent(valueEventListenerReservations);
    }
}
