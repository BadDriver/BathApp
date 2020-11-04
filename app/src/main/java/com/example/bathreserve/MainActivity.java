package com.example.bathreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.bathreserve.repositories.AccountRepository;
import com.example.bathreserve.viewModels.AccountViewModel;
import com.example.bathreserve.viewModels.HouseViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private AccountViewModel accountViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navBarFragments();
        accountViewModel = new ViewModelProvider(MainActivity.this).get(AccountViewModel.class);
        accountViewModel.getLoggedInLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showHomeFragment();
                }
                else {
                    showRegisterFragment();
                }
            }
        });
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
                        showHomeFragment();
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

    public void showHomeFragment(){
        accountViewModel.getOwnHouseLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(!aBoolean){
                    NoHouseFragment noHouseFragment = new NoHouseFragment();
                    fragmentTransaction.replace(R.id.frameLayout, noHouseFragment);
                }
                else{
                    ReservationsFragment reservationsFragment = new ReservationsFragment();
                    fragmentTransaction.replace(R.id.frameLayout, reservationsFragment);
                }
                fragmentTransaction.commit();
            }
        });
    }
}