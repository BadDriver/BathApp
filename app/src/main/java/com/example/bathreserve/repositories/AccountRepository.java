package com.example.bathreserve.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedInLiveData;
    private MutableLiveData<Boolean> ownHouseLiveData;

    public AccountRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedInLiveData = new MutableLiveData<>();
        this.ownHouseLiveData = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedInLiveData.postValue(true);
            ownHouseLiveData.postValue(false);
            checkUserOwnHouse();
        }
    }

    //registration, login, logout

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedInLiveData() {
        return loggedInLiveData;
    }

    public void register(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            loggedInLiveData.postValue(true);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            myRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(new User(email, name));
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void login(String email, String password) {
        ownHouseLiveData.postValue(false);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            loggedInLiveData.postValue(true);
                            checkUserOwnHouse();
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void logOut() {
        loggedInLiveData.postValue(false);
        firebaseAuth.signOut();
    }

    //house
    public void checkUserOwnHouse(){
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
