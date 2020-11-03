package com.example.bathreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bathreserve.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        navBarFragments();
    }

    public void showRegisterFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.add(R.id.frameLayout, registerFragment);
        fragmentTransaction.commit();
    }

    public void navBarFragments(){
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigationReservations:
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigationHouse:
                        AddHouseFragment addHouseFragment = new AddHouseFragment();
                        fragmentTransaction.replace(R.id.frameLayout, addHouseFragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigationProfile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentTransaction.replace(R.id.frameLayout, profileFragment);
                        fragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });
    }
}