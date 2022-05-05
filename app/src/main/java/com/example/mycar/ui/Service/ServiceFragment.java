package com.example.mycar.ui.Service;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.mycar.R;
import com.google.android.material.tabs.TabLayout;

public class ServiceFragment extends Fragment {

    private TabLayout tableLayout;
    public ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service, container, false);
        tableLayout = v.findViewById(R.id.tab_layout);
        viewPager = v.findViewById(R.id.viewpager);

        ServiceViewPagerAdapter adapter = new ServiceViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tableLayout.setupWithViewPager(viewPager);
        return v;
    }
}