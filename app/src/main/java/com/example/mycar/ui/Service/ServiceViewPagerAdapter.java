package com.example.mycar.ui.Service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mycar.ui.Service.Fragment.AddTableFragment;
import com.example.mycar.ui.Service.Fragment.ServiseTableFragment;
import com.example.mycar.ui.Service.Fragment.ZapravkaFragment;

import java.io.Serializable;

public class ServiceViewPagerAdapter extends FragmentStatePagerAdapter implements Serializable {
    public ServiceViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ZapravkaFragment();
            case 1:
                return new ServiseTableFragment();
            case 2:
                return new AddTableFragment();
            default:
                return new ZapravkaFragment();
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
                return "Заправка";
            case 1:
                return "Сервис";
            case 2:
                return "О сервисе";
            default:
                return "Заправка";
        }
    }
}
