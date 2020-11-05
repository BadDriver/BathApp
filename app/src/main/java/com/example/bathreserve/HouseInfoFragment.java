package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bathreserve.adapters.HouseUserListRecyclerViewAdapter;
import com.example.bathreserve.viewModels.AccountViewModel;
import com.example.bathreserve.viewModels.HouseViewModel;

import java.util.ArrayList;

public class HouseInfoFragment extends Fragment implements View.OnClickListener, HouseUserListRecyclerViewAdapter.ItemClickListener {
    HouseUserListRecyclerViewAdapter adapter;
    TextView textViewHouseInfoName;
    HouseViewModel houseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_house_info, container, false);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recylerViewHouseUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HouseUserListRecyclerViewAdapter(getContext(), animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        textViewHouseInfoName = view.findViewById(R.id.textViewHouseInfoName);
        houseViewModel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
        houseViewModel.getHouseName();
        houseViewModel.getHouseNameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewHouseInfoName.setText(s);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
