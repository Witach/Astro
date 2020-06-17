package com.example.myapplication;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TabletPagerAdapter extends MyFragmentPagerAdapter {
    private List<RefreshableFragment> fragments;
    private List<String> names;

    public TabletPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        init();
    }

    private void init(){
        fragments = new LinkedList<>();
        names = Arrays.asList("Sun and Moon","Adv Info", "Basic Info" , "Forecast");
        fragments.add(new SunAdnMoonInfo());
        fragments.add(new AdvancedWeatherInfo());
        fragments.add(new BasicWeatherInfo());
        fragments.add(new WeatherForecast());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<RefreshableFragment> getFragments(){
        return fragments;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void attachInfoToFragments(Object object){
        fragments.forEach(fragment -> {
            fragment.dataAttach(object);
        });
    }

    @Override
    public List<String> getFragmentsNames() {
        return names;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return names.get(position);
    }
}
