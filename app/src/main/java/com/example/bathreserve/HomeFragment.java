package com.example.bathreserve;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bathreserve.viewModels.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private TextView textViewHome;
    private Button buttonLogOut, buttonAddHouse;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
        loadUI(view);
        showUserName();
        buttonAddHouse.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
        return view;
    }

    private void loadUI(View view){
        textViewHome = view.findViewById(R.id.textViewEmail);
        buttonLogOut = view.findViewById(R.id.buttonHomeLogOut);
        buttonAddHouse = view.findViewById(R.id.buttonAddHouseFragment);
    }

    private void logOut(){
        loginRegisterViewModel.logOut();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentTransaction.replace(R.id.frameLayout, registerFragment);
        fragmentTransaction.commit();
    }

    private void showUserName(){
        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                textViewHome.setText(firebaseUser.getEmail());
            }
        });
    }

    private void addHouse(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddHouseFragment addHouseFragment = new AddHouseFragment();
        fragmentTransaction.replace(R.id.frameLayout, addHouseFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonHomeLogOut:
                logOut();
                break;
            case R.id.buttonAddHouseFragment:
                addHouse();
                break;
        }
    }
}
