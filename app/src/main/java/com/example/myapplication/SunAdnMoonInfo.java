package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SunAdnMoonInfo extends Fragment {
    public SunAdnMoonInfo() {
    }

    public static SunAdnMoonInfo newInstance(String param1, String param2) {
        SunAdnMoonInfo fragment = new SunAdnMoonInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sun_adn_moon_info, container, false);
    }
}
