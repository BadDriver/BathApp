package com.example.bathreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.bathreserve.repositories.AccountRepository;
import com.example.bathreserve.viewModels.AccountViewModel;
import com.example.bathreserve.viewModels.HouseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private AccountViewModel accountViewModel;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navBarFragments();
        accountViewModel = new ViewModelProvider(MainActivity.this).get(AccountViewModel.class);
        //if the user is logged in, it will go to showHomeFragment()
        accountViewModel.getLoggedInLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                    showRegisterFragment();
                }
                else{
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        //check if the user is part of a house or not
        accountViewModel.getOwnHouseLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                accountViewModel.setOwnHouse(aBoolean);
                showHomeFragment();
            }
        });
    }

    public void showRegisterFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.frameLayout, registerFragment);
        fragmentTransaction.commit();
    }

    public void navBarFragments(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigationReservations:
                        showHomeFragment();
                        break;
                    case R.id.navigationHouse:
                        showHouseInfoFragment();
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

    /**
     * Will show reservation fragment or no house fragment, depending if the current user is part of a house
     */
    public void showHomeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(accountViewModel.isOwnHouse()){
            ReservationsFragment reservationsFragment = new ReservationsFragment();
            fragmentTransaction.replace(R.id.frameLayout, reservationsFragment);
        }
        else{
            NoHouseFragment noHouseFragment = new NoHouseFragment();
            fragmentTransaction.replace(R.id.frameLayout, noHouseFragment);
        }
        fragmentTransaction.commit();
    }

    public void showHouseInfoFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(accountViewModel.isOwnHouse()){
            HouseInfoFragment houseInfoFragment = new HouseInfoFragment();
            fragmentTransaction.replace(R.id.frameLayout, houseInfoFragment);
        }
        else{
            AddHouseFragment addHouseFragment = new AddHouseFragment();
            fragmentTransaction.replace(R.id.frameLayout, addHouseFragment);
        }
        fragmentTransaction.commit();
    }
}