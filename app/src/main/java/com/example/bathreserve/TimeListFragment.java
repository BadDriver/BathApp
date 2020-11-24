package com.example.bathreserve;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bathreserve.adapters.TimeReservationListRecylerViewAdapter;
import com.example.bathreserve.models.Reservation;
import com.example.bathreserve.viewModels.ReservationViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimeListFragment extends Fragment implements TimeReservationListRecylerViewAdapter.ItemClickListener{
    private TimeReservationListRecylerViewAdapter adapter;
    private RecyclerView recyclerViewTimeList;
    public static final String ARG_OBJECT_ARRAY = "array";
    public static final String ARG_OBJECT_DAY = "day";
    private int receivedInt;
    private String dayOfWeek;
    private ReservationViewModel reservationViewModel;
    private ArrayList<Reservation> reservations;
    private ArrayList<Integer> reservationsHourFull;
    private ArrayList<Integer> reservationsHourHalf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_time_list, container, false);
        reservationsHourFull = new ArrayList<>();
        reservationsHourHalf = new ArrayList<>();
        reservationViewModel = new ViewModelProvider(getActivity()).get(ReservationViewModel.class);
        Bundle args = getArguments();
        reservations = (ArrayList<Reservation>) args.getSerializable(ARG_OBJECT_ARRAY);
        receivedInt = args.getInt(ARG_OBJECT_DAY);
        setDayOfWeek(receivedInt);
        for(Reservation r : reservations){
            if(r.getMinute() == 0){
                reservationsHourFull.add((r.getHour()));
            }
            else{
                reservationsHourHalf.add(r.getHour());
            }
        }
        fillList(view);
        return view;
    }

    private void setDayOfWeek(int receivedInt){
        switch(receivedInt) {
            case 0:
                dayOfWeek = "MONDAY";
                break;
            case 1:
                dayOfWeek = "TUESDAY";
                break;
            case 2:
                dayOfWeek = "WEDNESDAY";
                break;
            case 3:
                dayOfWeek = "THURSDAY";
                break;
            case 4:
                dayOfWeek = "FRIDAY";
                break;
            case 5:
                dayOfWeek = "SATURDAY";
                break;
            case 6:
                dayOfWeek = "SUNDAY";
                break;
        }
    }

    private void fillList(View view){
        List hours = new ArrayList<String>();
        List minutes = new ArrayList<String>();
        for(int i = 0; i <= 23; i++){
            if(!reservationsHourFull.contains(i)){
                if(i<10){
                    hours.add("0"+i);
                }
                else{
                    hours.add(i+"");
                }
                minutes.add(0+"0");
            }
            if(!reservationsHourHalf.contains(i)){
                if(i<10){
                    hours.add("0"+i);
                }
                else{
                    hours.add(i+"");
                }
                minutes.add(30+"");
            }
        }
        recyclerViewTimeList = view.findViewById(R.id.recyclerViewReservationTimes);
        recyclerViewTimeList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TimeReservationListRecylerViewAdapter(getContext(), hours, minutes);
        adapter.setClickListener(this);
        recyclerViewTimeList.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(View view, int position) {
        if(view.getId() == R.id.buttonReserveTime){
            //Toast.makeText(getContext(), "You clicked " + adapter.getHour(position) + " " + adapter.getMinute(position), Toast.LENGTH_SHORT).show();
            reservationViewModel.makeReservation(dayOfWeek, Integer.parseInt(adapter.getHour(position)), Integer.parseInt(adapter.getMinute(position)), false);
        }
    }
}