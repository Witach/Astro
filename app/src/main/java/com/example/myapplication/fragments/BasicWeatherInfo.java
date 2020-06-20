package com.example.myapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.jsonparse.YahooResponse;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.YahooRepository;

public class BasicWeatherInfo extends RefreshableFragment {
    TextView townName;
    TextView location;
    TextView time;
    TextView temperature;
    TextView presure;
    TextView discription;
    ImageView forecast_img;
    YahooResponse yahooResponse;
    YahooRepository yahooRepository;

    public BasicWeatherInfo() {
    }

    @Override
    public void dataAttach() {
        if(townName != null &&
                location != null &&
                time != null &&
                temperature != null &&
                presure != null &&
                discription != null ){
            yahooResponse = yahooRepository.getYahooResponse();
            refreshData();
        }
    }

    public void refreshData(){
        String locationString = "Long: %f Lat: %f";
        double lat = yahooResponse.getLocation().getLat();
        double log = yahooResponse.getLocation().getLog();
        townName.setText(yahooResponse.getLocation().getCity());
        location.setText(String.format(locationString, log, lat));
        time.setText(yahooResponse.getCurrent_observation().getPubDate().toString());
        temperature.setText(yahooResponse.getCurrent_observation().getCondition().getTemperature().toString());
        presure.setText(yahooResponse.getCurrent_observation().getAtmosphere().getPressure().toString());
        discription.setText(yahooResponse.getCurrent_observation().getCondition().getText());
    }


    public static BasicWeatherInfo newInstance(String param1, String param2) {
        BasicWeatherInfo fragment = new BasicWeatherInfo();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yahooRepository = DIManager.getInstance().getYahooRepository();
        yahooResponse = yahooRepository.getYahooResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_basic_weather_info, container, false);
        townName = viewGroup.findViewById(R.id.townName);
        location = viewGroup.findViewById(R.id.location);
        time = viewGroup.findViewById(R.id.time);
        temperature = viewGroup.findViewById(R.id.temperature);
        presure = viewGroup.findViewById(R.id.presure);
        discription = viewGroup.findViewById(R.id.discription);
        forecast_img = viewGroup.findViewById(R.id.forecast_img);
        refreshData();
        return viewGroup;
    }
}
