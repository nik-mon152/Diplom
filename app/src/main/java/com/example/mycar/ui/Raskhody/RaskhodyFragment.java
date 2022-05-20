package com.example.mycar.ui.Raskhody;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycar.R;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

class RaskhodyFragment extends Fragment {

    PieChart pieChart;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raskhody, container, false);

        pieChart = v.findViewById(R.id.pie_chart);



        return v;
    }
//    private loadPieChart(){
//        ArrayList<PieChart> pieCharts = new ArrayList<>();
//
//    }
}