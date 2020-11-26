package com.example.bathreserve;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bathreserve.viewModels.AccountViewModel;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private Button buttonLogOut;
    private AccountViewModel accountViewModel;
    private EditText editTextName, editTextEmail;
    private Button buttonSaveChanges;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);
        loadUI(view);
        showUserName();
        showUserEmail();
        buttonLogOut.setOnClickListener(this);
        buttonSaveChanges.setOnClickListener(this);
        return view;
    }

    private void loadUI(View view){
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonLogOut = view.findViewById(R.id.buttonHomeLogOut);
        buttonSaveChanges = view.findViewById(R.id.buttonSaveChanges);
    }

    private void logOut(){
        accountViewModel.logOut();
    }

    private void showUserEmail(){
        accountViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                editTextEmail.setText(firebaseUser.getEmail());
            }
        });
    }

    private void showUserName(){
        accountViewModel.getName();
        accountViewModel.getNameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editTextName.setText(s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonHomeLogOut:
                logOut();
                break;
            case R.id.buttonSaveChanges:
                accountViewModel.updateInfo(editTextEmail.getText().toString(), editTextName.getText().toString());
                break;
        }
    }
}
