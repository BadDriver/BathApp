package com.example.bathreserve.viewModels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.bathreserve.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedInLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        userLiveData = authRepository.getUserLiveData();
        loggedInLiveData = authRepository.getLoggedInLiveData();
    }

    public void login(String email, String password) {
        authRepository.login(email, password);
    }

    public void register(String email, String name, String password, String repeatPassword) {
        if(email.trim().isEmpty() || name.trim().isEmpty()
        || password.trim().isEmpty() || repeatPassword.trim().isEmpty()){
            Toast.makeText(getApplication(), "Please, complete all fields", Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(repeatPassword)){
            Toast.makeText(getApplication(), "Passwords don't match", Toast.LENGTH_LONG).show();
        }
        else{
            authRepository.register(email, password, name);
        }
    }

    public void logOut() {
        authRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedInLiveData() {
        return loggedInLiveData;
    }
}
