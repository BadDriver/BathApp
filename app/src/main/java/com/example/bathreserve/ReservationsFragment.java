package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ReservationsFragment extends Fragment implements View.OnClickListener {
    private Button buttonReservationsCreate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_reservations, container, false);
        loadViewObjects(view);
        buttonReservationsCreate.setOnClickListener(this);
        return view;
    }

    private void loadViewObjects(View view){
        buttonReservationsCreate = view.findViewById(R.id.buttonReservationsCreate);
    }

    private void createReservation(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReservationCreateFragment reservationCreateFragment = new ReservationCreateFragment();
        fragmentTransaction.replace(R.id.frameLayout, reservationCreateFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonReservationsCreate:
                createReservation();
                break;
        }
    }
}
