package com.example.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.jsonparse.YahooResponse;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.YahooRepository;


public class AdvancedWeatherInfo extends RefreshableFragment {
    TextView windForce;
    TextView  windDirection;
    TextView humidity;
    TextView visibility;
    YahooResponse data;
    YahooRepository yahooRepository;


    public AdvancedWeatherInfo() {
    }

    @Override
    public void dataAttach() {
            if(windForce != null &&
                windDirection != null &&
                humidity != null &&
                visibility != null) {
                data = yahooRepository.getYahooResponse();
            refreshData();
        }
    }

    public void refreshData(){
        windForce.setText(data.getCurrent_observation().getWind().getSpeed().toString());
        windDirection.setText(data.getCurrent_observation().getWind().getDirection().toString());
        humidity.setText(data.getCurrent_observation().getAtmosphere().getHumidity().toString());
        visibility.setText(data.getCurrent_observation().getAtmosphere().getVisibility().toString());
    }

    public static AdvancedWeatherInfo newInstance(String param1, String param2) {
        AdvancedWeatherInfo fragment = new AdvancedWeatherInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yahooRepository = DIManager.getInstance().getYahooRepository();
        data = yahooRepository.getYahooResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_advanced_weather_info, container, false);
        windForce =  viewGroup.findViewById(R.id.wind_force);
        windDirection =  viewGroup.findViewById(R.id.wind_direction);
        humidity =  viewGroup.findViewById(R.id.humidity);
        visibility =  viewGroup.findViewById(R.id.visability);
        refreshData();
        return  viewGroup;
    }
}
