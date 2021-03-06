package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.bathreserve.viewModels.HouseViewModel;

import java.util.ArrayList;
import java.util.List;

public class HouseInfoFragment extends Fragment implements View.OnClickListener, HouseUserListRecyclerViewAdapter.ItemClickListener {
    private HouseUserListRecyclerViewAdapter adapter;
    private TextView textViewHouseInfoName;
    private EditText editTextChangeTitle;
    private AutoCompleteTextView autoCompleteAddUser;
    private ImageView imageViewEditTitle;
    private ImageView imageViewSaveTitle;
    private Button buttonAddUser, buttonLeaveHouse;
    private HouseViewModel houseViewModel;
    private RecyclerView recyclerView;
    private List<String> userList;
    private ArrayList<String> allUsersEmail = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_house_info, container, false);
        houseViewModel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
        loadViewObjects(view);
        houseViewModel.getHouseInfo();
        getHouseName();
        getUserListNames();
        imageViewEditTitle.setOnClickListener(this);
        imageViewSaveTitle.setOnClickListener(this);
        buttonAddUser.setOnClickListener(this);
        buttonLeaveHouse.setOnClickListener(this);
        houseViewModel.getAllUsers();
        getAllUsersEmail();
        return view;
    }

    private void loadViewObjects(View view){
        textViewHouseInfoName = view.findViewById(R.id.textViewHouseInfoName);
        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.recylerViewHouseUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editTextChangeTitle = view.findViewById(R.id.editTextChangeTitle);
        imageViewEditTitle = view.findViewById(R.id.imageViewEditTitle);
        imageViewSaveTitle = view.findViewById(R.id.imageViewSaveTitle);
        buttonAddUser = view.findViewById(R.id.buttonAddUser);
        buttonLeaveHouse = view.findViewById(R.id.buttonLeaveHouse);
        autoCompleteAddUser = view.findViewById(R.id.autoCompleteAddUser);
    }

    private void getHouseName(){
        houseViewModel.getHouseNameLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewHouseInfoName.setText(s);
            }
        });
    }

    private void getUserListNames(){
        userList = new ArrayList<>();
        adapter = new HouseUserListRecyclerViewAdapter(getContext(), userList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        houseViewModel.getUsersNameListLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                userList.clear();
                for(String s : strings){
                    userList.add(s);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getAllUsersEmail(){
        houseViewModel.getAllUsersEmailLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                for(String s : strings){
                    allUsersEmail.add(s);
                }
            }
        });
        autoCompleteAddUser.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, allUsersEmail));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewEditTitle:
                textViewHouseInfoName.setVisibility(View.INVISIBLE);
                imageViewEditTitle.setVisibility(View.INVISIBLE);
                imageViewSaveTitle.setVisibility(View.VISIBLE);
                editTextChangeTitle.setVisibility(View.VISIBLE);
                editTextChangeTitle.requestFocus();
                editTextChangeTitle.setText(textViewHouseInfoName.getText().toString());
                break;
            case R.id.imageViewSaveTitle:
                houseViewModel.changeHouseName(editTextChangeTitle.getText().toString());
                textViewHouseInfoName.setVisibility(View.VISIBLE);
                imageViewEditTitle.setVisibility(View.VISIBLE);
                imageViewSaveTitle.setVisibility(View.INVISIBLE);
                editTextChangeTitle.setVisibility(View.INVISIBLE);
                break;
            case R.id.buttonAddUser:
                houseViewModel.addUser(autoCompleteAddUser.getText().toString());
                break;
            case R.id.buttonLeaveHouse:
                houseViewModel.leaveHouse();
                ((MainActivity)getActivity()).leaveHouse();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
