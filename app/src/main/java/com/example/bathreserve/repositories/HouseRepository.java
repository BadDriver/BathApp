package com.example.bathreserve.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HouseRepository {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<Boolean> ownHouseLiveData;
    private Application application;
    public HouseRepository(Application application) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.ownHouseLiveData = new MutableLiveData<>();
        this.ownHouseLiveData.postValue(false);
        this.application = application;
    }

    public void checkUserHouse(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
//                        Toast.makeText(application.getApplicationContext(), dataSnapshot.child("house").getValue().toString(), Toast.LENGTH_LONG).show();
//                        if(dataSnapshot.child("house").getValue() != null){
//                            Toast.makeText(application.getApplicationContext(), dataSnapshot.child("house").toString(), Toast.LENGTH_LONG).show();
//                            ownHouseLiveData.postValue(true);
//                        }
//                    }
//                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("pula", dataSnapshot.child("name").toString());
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Toast.makeText(application.getApplicationContext(), dataSnapshot1.child("name").toString(), Toast.LENGTH_LONG).show();
                        //Log.d("pula", dataSnapshot1.child("name").toString());
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
