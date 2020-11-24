package com.example.bathreserve;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bathreserve.adapters.TimeReservationListRecylerViewAdapter;
import com.example.bathreserve.models.Reservation;
import com.example.bathreserve.viewModels.ReservationViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationsFragment extends Fragment {
    private DemoCollectionAdapter demoCollectionAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ReservationViewModel reservationViewModel;
    private List<Reservation> reservationsMonday;
    private List<Reservation> reservationsTuesday;
    private List<Reservation> reservationsWednesday;
    private List<Reservation> reservationsThursday;
    private List<Reservation> reservationsFriday;
    private List<Reservation> reservationsSaturday;
    private List<Reservation> reservationsSunday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_reservations, container, false);
        reservationViewModel = new ViewModelProvider(getActivity()).get(ReservationViewModel.class);
        reservationsMonday = new ArrayList<>();
        reservationsTuesday = new ArrayList<>();
        reservationsWednesday = new ArrayList<>();
        reservationsThursday = new ArrayList<>();
        reservationsFriday = new ArrayList<>();
        reservationsSaturday = new ArrayList<>();
        reservationsSunday = new ArrayList<>();
        reservationViewModel.fetchReservations();
        fillReservations(view);
        return view;
    }

    //inside here, also loadViewObjects() it's called so the viewPage adapter is shown only after the data was fetched
    private void fillReservations(View view){
        reservationViewModel.getReservationsListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Reservation>>() {
            @Override
            public void onChanged(List<Reservation> reservations) {
                for(Reservation r : reservations){
                    switch (r.getDayOfWeek()){
                        case "MONDAY":
                            reservationsMonday.add(r);
                            break;
                        case "TUESDAY":
                            reservationsTuesday.add(r);
                            break;
                        case "WEDNESDAY":
                            reservationsWednesday.add(r);
                            break;
                        case "THURSDAY":
                            reservationsThursday.add(r);
                            break;
                        case "FRIDAY":
                            reservationsFriday.add(r);
                            break;
                        case "SATURDAY":
                            reservationsSaturday.add(r);
                            break;
                        case "SUNDAY":
                            reservationsSunday.add(r);
                            break;
                    }
                }
                loadViewObjects(view);
            }
        });
    }

    private void loadViewObjects(View view){
        demoCollectionAdapter = new DemoCollectionAdapter(this);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(demoCollectionAdapter);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(1);
                break;
            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:
                viewPager.setCurrentItem(3);
                break;
            case Calendar.FRIDAY:
                viewPager.setCurrentItem(4);
                break;
            case Calendar.SATURDAY:
                viewPager.setCurrentItem(5);
                break;
            case Calendar.SUNDAY:
                viewPager.setCurrentItem(6);
                break;
        }
        tabLayout = view.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("Mon");
                        break;
                    case 1:
                        tab.setText("Tue");
                        break;
                    case 2:
                        tab.setText("Wed");
                        break;
                    case 3:
                        tab.setText("Thu");
                        break;
                    case 4:
                        tab.setText("Fri");
                        break;
                    case 5:
                        tab.setText("Sat");
                        break;
                    case 6:
                        tab.setText("Sun");
                        break;
                }
            }
        }).attach();
    }

    public class DemoCollectionAdapter extends FragmentStateAdapter {
        public DemoCollectionAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new TimeListFragment();
            Bundle args = new Bundle();
            switch(position) {
                case 0:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsMonday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 0);
                    break;
                case 1:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsTuesday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 1);
                    break;
                case 2:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsWednesday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 2);
                    break;
                case 3:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsThursday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 3);
                    break;
                case 4:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsFriday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 4);
                    break;
                case 5:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsSaturday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 5);
                    break;
                case 6:
                    args.putSerializable(TimeListFragment.ARG_OBJECT_ARRAY, (Serializable) reservationsSunday);
                    args.putInt(TimeListFragment.ARG_OBJECT_DAY, 6);
                    break;
            }
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }
}
