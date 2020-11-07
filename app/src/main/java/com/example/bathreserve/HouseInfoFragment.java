package com.example.bathreserve;

import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class HouseInfoFragment extends Fragment implements View.OnClickListener, HouseUserListRecyclerViewAdapter.ItemClickListener {
    private HouseUserListRecyclerViewAdapter adapter;
    private TextView textViewHouseInfoName;
    private HouseViewModel houseViewModel;
    private RecyclerView recyclerView;
    private List<String> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_house_info, container, false);
        houseViewModel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
        getHouseName();
        getUserListHouse();
        loadViewObjects(view);
        return view;
    }

    private void loadViewObjects(View view){
        textViewHouseInfoName = view.findViewById(R.id.textViewHouseInfoName);
        userList = new ArrayList<>();
        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.recylerViewHouseUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HouseUserListRecyclerViewAdapter(getContext(), userList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void getHouseName(){
        houseViewModel.getHouseName();
        houseViewModel.getHouseNameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewHouseInfoName.setText(s);
            }
        });
    }

    public void getUserListHouse(){
        houseViewModel.getUserListHouse();
        houseViewModel.getUsersListHouseLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                for(String s : strings){
                    userList.add(s);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
