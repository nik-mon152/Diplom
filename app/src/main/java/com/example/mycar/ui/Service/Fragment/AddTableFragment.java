package com.example.mycar.ui.Service.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycar.Authentication.LoginActivity;
import com.example.mycar.R;
import com.example.mycar.ui.Car.AddCar;
import com.example.mycar.ui.Service.AddingService.AddZapravka;

public class AddTableFragment extends Fragment {

    TextView textZapravki, textService, textInfo;
    ImageView imgZapravki, imgService, imgInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_table, container, false);

        textZapravki = v.findViewById(R.id.textAddZapravki);
        textService = v.findViewById(R.id.textAddService);
        textInfo = v.findViewById(R.id.textInfoService);

        imgZapravki = v.findViewById(R.id.AddZapravka);
        imgService = v.findViewById(R.id.AddService);
        imgInfo = v.findViewById(R.id.InfoService);

        textZapravki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(v.getContext(), AddZapravka.class));
            }
        });
        imgZapravki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(v.getContext(), AddZapravka.class));
            }
        });

        textService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        textInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return v;
    }

}