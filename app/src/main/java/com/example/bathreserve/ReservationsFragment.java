package com.example.bathreserve;

import android.os.Bundle;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bathreserve.adapters.TimeReservationListRecylerViewAdapter;
import com.example.bathreserve.viewModels.ReservationViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {
    private DemoCollectionAdapter demoCollectionAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_reservations, container, false);
        loadViewObjects(view);
        return view;
    }

    private void loadViewObjects(View view){
        demoCollectionAdapter = new DemoCollectionAdapter(this);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(demoCollectionAdapter);
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
            // Return a NEW fragment instance in createFragment(int)
            Fragment fragment = new TimeListFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(TimeListFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 7;
        }
    }
}
