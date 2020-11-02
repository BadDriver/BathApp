package com.example.bathreserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.example.bathreserve.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRegisterFragment();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

//        myRef.setValue("Hello, Worldddddddd!");
//        List<String> ham = new ArrayList<>();
//        ham.add("catel");
//        ham.add("purcel");
//        myRef.child("catel").child("cat").push().child("name").setValue("slobozel");
//        myRef.child("catel").child("cat").push().child("email").setValue("slobozica");
//        List<User> users = new ArrayList<>();
//        users.add(new User("1", "1"));
//        users.add(new User("2", "2"));
//        users.add(new User("3", "3"));
//        myRef.setValue(users);

    }

    public void showRegisterFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.add(R.id.frameLayout, registerFragment);
        fragmentTransaction.commit();
    }
}