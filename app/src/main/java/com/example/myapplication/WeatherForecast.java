package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.jsonparse.YahooResponse;


public class WeatherForecast extends RefreshableFragment {
    public WeatherForecast() {
    }

    @Override
    public void dataAttach(YahooResponse object) {

    }

    public static WeatherForecast newInstance(String param1, String param2) {
        WeatherForecast fragment = new WeatherForecast();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false);
    }
}
