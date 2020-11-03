package com.example.bathreserve;

import android.os.Bundle;
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

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private TextView textViewEmail;
    private Button buttonLogOut;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
        loadUI(view);
        showUserName();
        buttonLogOut.setOnClickListener(this);
        return view;
    }

    private void loadUI(View view){
        textViewEmail = view.findViewById(R.id.textViewEmail);
        buttonLogOut = view.findViewById(R.id.buttonHomeLogOut);
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
                textViewEmail.setText(firebaseUser.getEmail());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonHomeLogOut:
                logOut();
                break;
        }
    }
}
