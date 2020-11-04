package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bathreserve.viewModels.HouseViewModel;

public class AddHouseFragment extends Fragment implements View.OnClickListener {
    private EditText editTextHouseNameAdd;
    private Button buttonHouseAdd;
    HouseViewModel houseViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_house, container, false);
        loadUI(view);
        buttonHouseAdd.setOnClickListener(this);
        houseViewModel = new ViewModelProvider(getActivity()).get(HouseViewModel.class);
        return view;
    }

    private void loadUI(View view){
        editTextHouseNameAdd = view.findViewById(R.id.editTextHouseAddName);
        buttonHouseAdd = view.findViewById(R.id.buttonHouseAdd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonHouseAdd:
                houseViewModel.addHouse(editTextHouseNameAdd.getText().toString());
                break;
        }
    }
}
