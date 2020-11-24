package com.example.bathreserve.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bathreserve.models.Reservation;
import com.example.bathreserve.repositories.ReservationRepository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

public class ReservationViewModel extends AndroidViewModel {
    private ReservationRepository reservationRepository;
    private MutableLiveData<List<Reservation>> reservationsListLiveData;

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        this.reservationRepository = new ReservationRepository();
        this.reservationsListLiveData = reservationRepository.getReservationsListLiveData();
    }

    public MutableLiveData<List<Reservation>> getReservationsListLiveData() {
        return reservationsListLiveData;
    }

    public void makeReservation(String dayOfWeek, int hour, int minute, Boolean repeat){
        reservationRepository.makeReservation(dayOfWeek, hour, minute, repeat);
    }

    public void fetchReservations(){
        reservationRepository.fetchReservations();
    }
}
