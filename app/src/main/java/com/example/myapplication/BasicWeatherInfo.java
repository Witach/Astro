package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.jsonparse.YahooResponse;

public class BasicWeatherInfo extends RefreshableFragment {
    public BasicWeatherInfo() {
    }

    @Override
    public void dataAttach(YahooResponse object) {

    }

    public static BasicWeatherInfo newInstance(String param1, String param2) {
        BasicWeatherInfo fragment = new BasicWeatherInfo();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_weather_info, container, false);
    }
}
