package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FullWeatherInfo extends Fragment {
    public FullWeatherInfo() {
    }

    public static FullWeatherInfo newInstance(String param1, String param2) {
        FullWeatherInfo fragment = new FullWeatherInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_weather_info, container, false);
    }
}
