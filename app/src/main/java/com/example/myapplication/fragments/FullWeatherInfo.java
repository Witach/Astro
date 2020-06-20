package com.example.myapplication.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.jsonparse.YahooResponse;


public class FullWeatherInfo extends RefreshableFragment {
    public FullWeatherInfo() {
    }

    public static FullWeatherInfo newInstance(String param1, String param2) {
        return new FullWeatherInfo();
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

    @Override
    public void dataAttach() {

    }
}
