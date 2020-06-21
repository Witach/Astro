package com.example.myapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.jsonparse.ForecastDay;

import java.time.Instant;
import java.time.ZoneId;

public class YahooDataFormatter {
    Units selectedUnitsType;

    public YahooDataFormatter() {
    }

    public Units getSelectedUnitsType() {
        return selectedUnitsType;
    }

    public void setSelectedUnitsType(Units selectedUnitsType) {
        this.selectedUnitsType = selectedUnitsType;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String formatTime(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }

    public String formatTemperature(double temperature) {
        String unit = selectedUnitsType == Units.F ? "F" : "C";
        return String.format("%.2f %s", temperature, unit);
    }

    public String formatPresure(double pressure) {
        String unit = selectedUnitsType == Units.F ? "inHg" : "hPa";
        return String.format("%.2f %s", pressure, unit);
    }

    public String formatWindForce(double windForce) {
        String unit = selectedUnitsType == Units.F ? "m/h" : "km/h";
        return String.format("%.2f %s", windForce, unit);
    }

    public String formatWindDirection(double direction) {
        if (direction > 315 || direction <= 45)
            return "N";
        else if (direction > 45 && direction <= 135)
            return "E";
        else if (direction > 135 && direction <= 225)
            return "S";
        else
            return "W";
    }

    public String formatHumidity(double humidity) {
        return String.format("%.2f %%", humidity);
    }

    public String formatVisibility(double visibility) {
        String unit = selectedUnitsType == Units.F ? "m" : "km";
        return String.format("%.2f %s", visibility, unit);
    }

    ;

    public String formatForecast(ForecastDay forecastDay) {
        String unit = selectedUnitsType == Units.F ? "F" : "C";
        return String.format("Lowest: %s %s\n Highest: %s %s\n Desc: %s", forecastDay.getLow(), unit,
                forecastDay.getHigh(), unit,
                forecastDay.getText());
    }

    public int getApropiateImgForDesc(String description){
        String lowerCaseDescription = description.toLowerCase();
        if(lowerCaseDescription.contains("rain") || lowerCaseDescription.contains("showers"))
            return R.drawable.rainy;
        else if(lowerCaseDescription.contains("cloud"))
            return R.drawable.cloudy;
        else if(lowerCaseDescription.contains("storm"))
            return R.drawable.stormy;
        else
            return R.drawable.sunny;

    }
}
