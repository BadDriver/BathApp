package com.example.bathreserve.repositories;

import androidx.annotation.NonNull;

import com.example.bathreserve.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;

public class ReservationRepository {
    private FirebaseAuth firebaseAuth;
    private String houseId;

    public ReservationRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void makeReservation(DayOfWeek dayOfWeek, int hour, int minute, Boolean repeat){
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
}
