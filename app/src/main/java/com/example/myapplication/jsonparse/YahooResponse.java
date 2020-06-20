package com.example.myapplication.jsonparse;

import java.util.List;

public class YahooResponse {
    Location location;
    CurrentObservation current_observation;
    List<ForecastDay> forecast;
    Long pubDate;

    public YahooResponse() {
    }

    public YahooResponse(Location location, CurrentObservation current_observation, List<ForecastDay> forecast, Long pubDate) {
        this.location = location;
        this.current_observation = current_observation;
        this.forecast = forecast;
        this.pubDate = pubDate;
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

    public List<ForecastDay> getForecast() {
        return forecast;
    }

    public void setForecast(List<ForecastDay> forecast) {
        this.forecast = forecast;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "YahooResponse{" +
                "location=" + location +
                ", current_observation=" + current_observation +
                ", forecast=" + forecast +
                ", pubDate=" + pubDate +
                '}';
    }
}
