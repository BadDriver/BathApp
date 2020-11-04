package com.example.bathreserve;

import android.os.Bundle;
import android.util.Log;
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

import com.example.bathreserve.viewModels.AccountViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LogInFragment extends Fragment implements View.OnClickListener{
    private EditText emailLogInEditText, passwordLogInEditText;
    private Button submitLogInButton;
    private AccountViewModel accountViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);
        loadViewObjects(view);
        submitLogInButton.setOnClickListener(this);
        return view;
    }

    public void loadViewObjects(View view){
        emailLogInEditText = view.findViewById(R.id.editTextEmailLogIn);
        passwordLogInEditText = view.findViewById(R.id.editTextPasswordLogIn);
        submitLogInButton = view.findViewById(R.id.buttonSubmitLogIn);
    }

    public void submitLogInButton(){
        accountViewModel.login(emailLogInEditText.getText().toString(), passwordLogInEditText.getText().toString());
        showHomeFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSubmitLogIn:
                submitLogInButton();
                break;
        }
    }

    /**
     * Method used to go to home fragment after the user has registered
     * Also used to skip the registration fragment if the user is already logged in
     */
    public void showHomeFragment(){
        accountViewModel.getOwnHouseLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                FragmentManager fragmentManager = getParentFragmentManager();
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
