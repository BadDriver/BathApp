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
import android.widget.Toast;

import com.example.bathreserve.repositories.HouseRepository;
import com.example.bathreserve.viewModels.HouseViewModel;
import com.example.bathreserve.viewModels.LoginRegisterViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private HouseViewModel houseViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRegisterFragment();
        navBarFragments();
        houseViewModel = new ViewModelProvider(MainActivity.this).get(HouseViewModel.class);
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
                        showHome();
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

    public void showHome(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        houseViewModel.getOwnHouseLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //Toast.makeText(MainActivity.this, aBoolean.toString(), Toast.LENGTH_LONG).show();
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