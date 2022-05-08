package com.example.mycar.ui.Service.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycar.R;


public class ZapravkaFragment extends Fragment {

    RecyclerView zapravkaLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zapravka, container, false);

        zapravkaLists = v.findViewById(R.id.zapravralist);

        return v;
    }
}