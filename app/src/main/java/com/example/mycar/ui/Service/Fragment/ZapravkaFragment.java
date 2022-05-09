package com.example.mycar.ui.Service.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.mycar.R;
import com.example.mycar.ui.Service.ModelZapravka.AdapterZapravka;

import java.util.ArrayList;
import java.util.List;


public class ZapravkaFragment extends Fragment {

    RecyclerView zapravkaLists;
    AdapterZapravka adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zapravka, container, false);

        zapravkaLists = v.findViewById(R.id.zapravralist);

        List<String> fuels = new ArrayList<>();
        List<String> cumms = new ArrayList<>();
        List<String> litrs = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> probegs = new ArrayList<>();
        List<String> comments = new ArrayList<>();

        fuels.add("АИ-95");
        cumms.add("2000Р");
        litrs.add("50л");
        prices.add("50Р");
        probegs.add("120000");
        comments.add("авпвпвввввпывар");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        fuels.add("АИ-92");
        cumms.add("1500Р");
        litrs.add("45л");
        prices.add("52Р");
        probegs.add("130000");
        comments.add("GHbdfg");

        adapter = new AdapterZapravka(fuels, cumms, litrs, prices, probegs, comments);
        zapravkaLists.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        zapravkaLists.setAdapter(adapter);

        return v;
    }
}