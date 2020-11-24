package com.example.bathreserve.repositories;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.Reservation;
import com.example.bathreserve.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private FirebaseAuth firebaseAuth;
    private String houseId;
    private MutableLiveData<List<Reservation>> reservationsListLiveData;

    public ReservationRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        reservationsListLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Reservation>> getReservationsListLiveData() {
        return reservationsListLiveData;
    }

    public void makeReservation(String dayOfWeek, int hour, int minute, Boolean repeat){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceHouse = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())){
                        houseId = dataSnapshot.child("house").getValue().toString();
                        DatabaseReference databaseReferenceReservation = database.getReference("reservations");
                        String reservationKey = databaseReferenceReservation.push().getKey();
                        databaseReferenceReservation.child(houseId).child(reservationKey).child("userId").setValue(firebaseAuth.getCurrentUser().getUid());
                        databaseReferenceReservation.child(houseId).child(reservationKey).child("day").setValue(dayOfWeek);
                        databaseReferenceReservation.child(houseId).child(reservationKey).child("hour").setValue(hour);
                        databaseReferenceReservation.child(houseId).child(reservationKey).child("minute").setValue(minute);
                        fetchReservations();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceHouse.addListenerForSingleValueEvent(valueEventListener);
    }

    public void fetchReservations(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceHouse = database.getReference("users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                        houseId = dataSnapshot.child("house").getValue().toString();
                        Log.d("poloz", houseId);
                        break;
                    }
                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReferenceHouse = database.getReference("reservations");
                List<Reservation> tempList = new ArrayList<>();
                ValueEventListener valueEventListenerReservations = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.getKey().equals(houseId)){
                                for(DataSnapshot dataSnapshotReservations : dataSnapshot.getChildren()){
                                    String dayOfWeek = dataSnapshotReservations.child("day").getValue().toString();
                                    int hour = Integer.parseInt(String.valueOf(dataSnapshotReservations.child("hour").getValue()));
                                    int minute = Integer.parseInt(String.valueOf(dataSnapshotReservations.child("minute").getValue()));
                                    String userId = firebaseAuth.getCurrentUser().getUid();
                                    Reservation reservation = new Reservation(dayOfWeek, hour, minute, userId);
                                    tempList.add(reservation);
                                }
                                reservationsListLiveData.postValue(tempList);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                databaseReferenceHouse.addListenerForSingleValueEvent(valueEventListenerReservations);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReferenceHouse.addListenerForSingleValueEvent(valueEventListener);
    }
}
