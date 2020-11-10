package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.bathreserve.repositories.ReservationRepository;

import java.sql.Time;
import java.time.DayOfWeek;

public class ReservationViewModel extends AndroidViewModel {
    ReservationRepository reservationRepository;
    public ReservationViewModel(@NonNull Application application) {
        super(application);
        this.reservationRepository = new ReservationRepository();
    }

    public void makeReservation(DayOfWeek dayOfWeek, int hour, int minute, Boolean repeat){
        reservationRepository.makeReservation(dayOfWeek, hour, minute, repeat);
    }
}
