package com.example.bathreserve.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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

    //house
    public void checkUserOwnHouse(){
        ownHouseLiveData.postValue(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                        if(dataSnapshot.child("house").getValue() != null){
                            ownHouseLiveData.postValue(true);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addValueEventListener(valueEventListener);
    }

    public MutableLiveData<Boolean> getOwnHouseLiveData() {
        return ownHouseLiveData;
    }
}
