package com.example.mycar.ui.Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ServiceViewPagerAdapter extends FragmentStatePagerAdapter {
    public ServiceViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AddTableFragment();
            case 1:
                return new ZapravkaFragment();
            case 2:
                return new ServiseTableFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Добавить";
            case 1:
                return "Заправка";
            case 2:
                return "Сервис";
            default:
                return "Добавить";
        }
    }
}
