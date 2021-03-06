package com.example.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.jsonparse.YahooResponse;


public class SunAdnMoonInfo extends RefreshableFragment {
    SunFragment sunFragment;
    MoonFragment moonFragment;
    public SunAdnMoonInfo() {
    }

    public static SunAdnMoonInfo newInstance(String param1, String param2) {
        SunAdnMoonInfo fragment = new SunAdnMoonInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sunFragment = (SunFragment) getFragmentManager().findFragmentById(R.id.sun_fragment);
        moonFragment = (MoonFragment) getFragmentManager().findFragmentById(R.id.moon_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sun_adn_moon_info, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataAttach() {
        if(sunFragment!= null && moonFragment != null) {
            sunFragment.dataAttach();
            moonFragment.dataAttach();
        }
    }
}
