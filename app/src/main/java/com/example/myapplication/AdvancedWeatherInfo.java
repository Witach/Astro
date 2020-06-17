package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AdvancedWeatherInfo extends RefreshableFragment {


    public AdvancedWeatherInfo() {
    }

    @Override
    public void dataAttach(Object object) {

    }

    public static AdvancedWeatherInfo newInstance(String param1, String param2) {
        AdvancedWeatherInfo fragment = new AdvancedWeatherInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advanced_weather_info, container, false);
    }
}