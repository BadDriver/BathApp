package com.example.bathreserve.viewModels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.bathreserve.repositories.AccountRepository;
import com.google.firebase.auth.FirebaseUser;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedInLiveData;
    private MutableLiveData<Boolean> ownHouseLiveData;
    private boolean ownHouse;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        this.accountRepository = new AccountRepository(application);
        this.userLiveData = accountRepository.getUserLiveData();
        this.loggedInLiveData = accountRepository.getLoggedInLiveData();
        this.ownHouseLiveData = accountRepository.getOwnHouseLiveData();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedInLiveData() {
        return loggedInLiveData;
    }

    public MutableLiveData<Boolean> getOwnHouseLiveData() {
        return ownHouseLiveData;
    }

    public boolean isOwnHouse() {
        return ownHouse;
    }

    public void setOwnHouse(boolean ownHouse) {
        this.ownHouse = ownHouse;
    }

    public void login(String email, String password) {
        if(email.trim().isEmpty() || password.trim().isEmpty()){
            Toast.makeText(getApplication(), "Please, complete all fields", Toast.LENGTH_LONG).show();
        }
        else{
            accountRepository.login(email, password);
        }
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
            accountRepository.register(email, password, name);
        }
    }

    public void logOut() {
        accountRepository.logOut();
    }

    public void leaveHouse(){
        accountRepository.userLeaveHouse();
    }
}
