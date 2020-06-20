package com.example.myapplication.jsonparse;

import java.util.List;

public class YahooResponse {
    Location location;
    CurrentObservation current_observation;
    List<ForecastDay> forecasts;

    public YahooResponse() {
    }

    public YahooResponse(Location location, CurrentObservation current_observation, List<ForecastDay> forecasts) {
        this.location = location;
        this.current_observation = current_observation;
        this.forecasts = forecasts;

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CurrentObservation getCurrent_observation() {
        return current_observation;
    }

    public void setCurrent_observation(CurrentObservation current_observation) {
        this.current_observation = current_observation;
    }

    public List<ForecastDay> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastDay> forecasts) {
        this.forecasts = forecasts;
    }


    @Override
    public String toString() {
        return "YahooResponse{" +
                "location=" + location +
                ", current_observation=" + current_observation +
                ", forecast=" + forecasts +
                '}';
    }
}
