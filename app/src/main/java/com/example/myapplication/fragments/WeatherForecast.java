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
import com.example.myapplication.service.YahooDataFormatter;
import com.example.myapplication.service.YahooRepository;


public class WeatherForecast extends RefreshableFragment {
    TextView tomorrow;
    TextView secondDay;
    TextView thirdDay;
    TextView fourthDay;
    ImageView tomorrowImg;
    ImageView secondDayImg;
    ImageView thirdDayImg;
    ImageView fourthDayImg;
    TextView thirdDayDate;
    TextView fourthDayDate;
    YahooResponse yahooResponse;
    YahooDataFormatter yahooDataFormatter;
    YahooRepository yahooRepository;
    public WeatherForecast() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataAttach() {
        if(tomorrowImg !=null){
            refreshData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void refreshData(){
        yahooResponse = yahooRepository.getYahooResponse();
        tomorrow.setText(yahooDataFormatter.formatForecast(yahooResponse.getForecasts().get(1)));
        secondDay.setText(yahooDataFormatter.formatForecast(yahooResponse.getForecasts().get(2)));
        thirdDay.setText(yahooDataFormatter.formatForecast(yahooResponse.getForecasts().get(3)));
        fourthDay.setText(yahooDataFormatter.formatForecast(yahooResponse.getForecasts().get(4)));
        thirdDayDate.setText(yahooResponse.getForecasts().get(3).getDay());
        fourthDayDate.setText(yahooResponse.getForecasts().get(4).getDay());
        tomorrowImg.setImageResource(yahooDataFormatter.getApropiateImgForDesc(yahooResponse.getForecasts().get(1).getText()));
        secondDayImg.setImageResource(yahooDataFormatter.getApropiateImgForDesc(yahooResponse.getForecasts().get(2).getText()));
        thirdDayImg.setImageResource(yahooDataFormatter.getApropiateImgForDesc(yahooResponse.getForecasts().get(3).getText()));
        fourthDayImg.setImageResource(yahooDataFormatter.getApropiateImgForDesc(yahooResponse.getForecasts().get(4).getText()));
    }

    public static WeatherForecast newInstance(String param1, String param2) {
        WeatherForecast fragment = new WeatherForecast();
        Bundle args = new Bundle();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DIManager diManager = DIManager.getInstance();
        yahooDataFormatter = diManager.getYahooDataFormatter();
        yahooRepository = diManager.getYahooRepository();
        yahooResponse = yahooRepository.getYahooResponse();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        tomorrow = viewGroup.findViewById(R.id.first_day_forecast);
        secondDay = viewGroup.findViewById(R.id.second_day_forecast);
        thirdDay = viewGroup.findViewById(R.id.third_day_forecast);
        fourthDay = viewGroup.findViewById(R.id.fourth_day_forecast);
        thirdDayDate = viewGroup.findViewById(R.id.third_day_date);
        fourthDayDate = viewGroup.findViewById(R.id.fourth_day_date);
        tomorrowImg = viewGroup.findViewById(R.id.first_day_img);
        secondDayImg = viewGroup.findViewById(R.id.second_day_img);
        thirdDayImg = viewGroup.findViewById(R.id.third_day_img);
        fourthDayImg = viewGroup.findViewById(R.id.fourth_day_img);
        refreshData();
        return viewGroup;
    }
}
