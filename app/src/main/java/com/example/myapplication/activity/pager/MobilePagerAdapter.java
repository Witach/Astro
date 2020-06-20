package com.example.myapplication.activity.pager;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.fragments.AdvancedWeatherInfo;
import com.example.myapplication.fragments.BasicWeatherInfo;
import com.example.myapplication.fragments.MoonFragment;
import com.example.myapplication.fragments.SunFragment;
import com.example.myapplication.fragments.WeatherForecast;
import com.example.myapplication.jsonparse.YahooResponse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MobilePagerAdapter extends MyFragmentPagerAdapter{
    private List<RefreshableFragment> fragments;
    private List<String> names;

    public MobilePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        init();
    }

    private void init(){
        fragments = new LinkedList<>();
        names = Arrays.asList("Sun","Moon","Basic Weather", "Adv Weather", "Forecast");
        fragments.add(new SunFragment());
        fragments.add(new MoonFragment());
        fragments.add(new BasicWeatherInfo());
        fragments.add(new AdvancedWeatherInfo());
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
