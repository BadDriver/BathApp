package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.bathreserve.viewModels.LoginRegisterViewModel;



public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText emailRegisterEditText, nameRegisterEditText, passwordRegisterEditText, repeatPasswordRegisterEditText;
    private Button submitRegisterButton, logInRegisterButton;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        loginRegisterViewModel = new ViewModelProvider(getActivity()).get(LoginRegisterViewModel.class);
        loadViewObjects(view);
        submitRegisterButton.setOnClickListener(this);
        logInRegisterButton.setOnClickListener(this);
        showHomeFragment();
        return view;
    }

    public void submitRegisterButton(){
        loginRegisterViewModel.register(emailRegisterEditText.getText().toString(), nameRegisterEditText.getText().toString(),
                passwordRegisterEditText.getText().toString(), repeatPasswordRegisterEditText.getText().toString());
    }

    public void logInButton(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogInFragment logInFragment = new LogInFragment();
        fragmentTransaction.replace(R.id.frameLayout, logInFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadViewObjects(View view){
        emailRegisterEditText = view.findViewById(R.id.editTextEmailRegister);
        nameRegisterEditText = view.findViewById(R.id.editTextNicknameRegister);
        passwordRegisterEditText = view.findViewById(R.id.editTextPasswordRegister);
        repeatPasswordRegisterEditText = view.findViewById(R.id.editTextPasswordRepeatRegister);
        submitRegisterButton = view.findViewById(R.id.buttonSubmitRegister);
        logInRegisterButton = view.findViewById(R.id.buttonLogInRegister);
    }

    /**
     * Method used to go to home fragment after the user has registered
     * Also used to skip the registration fragment if the user is already logged in
     */
    public void showHomeFragment(){
        loginRegisterViewModel.getLoggedInLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedIn) {
                if(loggedIn){
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.frameLayout, homeFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSubmitRegister:
                submitRegisterButton();
                break;
            case R.id.buttonLogInRegister:
                logInButton();
                break;
        }
    }
}