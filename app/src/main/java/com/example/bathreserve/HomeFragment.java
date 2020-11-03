package com.example.bathreserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button buttonAddHouse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        loadUI(view);
        buttonAddHouse.setOnClickListener(this);
        return view;
    }

    private void loadUI(View view){
        buttonAddHouse = view.findViewById(R.id.buttonAddHouseFragment);
    }

    private void addHouse(){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddHouseFragment addHouseFragment = new AddHouseFragment();
        fragmentTransaction.replace(R.id.frameLayout, addHouseFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddHouseFragment:
                addHouse();
                break;
        }
    }
}
