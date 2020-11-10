package com.example.bathreserve;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bathreserve.viewModels.AccountViewModel;
import com.example.bathreserve.viewModels.ReservationViewModel;

import java.sql.Time;
import java.time.DayOfWeek;

public class ReservationCreateFragment extends Fragment implements View.OnClickListener{
    private Button buttonReserveMonday1330, buttonReserveMonday1400, buttonReserveTuesday1330;
    private ReservationViewModel reservationViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reservation_create, container, false);
        reservationViewModel = new ViewModelProvider(getActivity()).get(ReservationViewModel.class);
        loadViewObjects(view);
        buttonReserveMonday1330.setOnClickListener(this);
        buttonReserveMonday1400.setOnClickListener(this);
        buttonReserveTuesday1330.setOnClickListener(this);
        return view;
    }

    private void loadViewObjects(View view){
        buttonReserveMonday1330 = view.findViewById(R.id.buttonReserveMonday1330);
        buttonReserveMonday1400 = view.findViewById(R.id.buttonReserveMonday1400);
        buttonReserveTuesday1330 = view.findViewById(R.id.buttonReserveTuesday1330);
    }

    private void makeReservation(DayOfWeek dayOfWeek, int hour, int minute, Boolean repeat){
        reservationViewModel.makeReservation(dayOfWeek, hour, minute, repeat);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonReserveMonday1330:
                makeReservation(DayOfWeek.MONDAY, 13, 30, false);
                break;
            case R.id.buttonReserveMonday1400:
                makeReservation(DayOfWeek.MONDAY, 14, 00, false);
                break;
            case R.id.buttonReserveTuesday1330:
                makeReservation(DayOfWeek.TUESDAY, 13, 30, false);
                break;
        }
    }
}
