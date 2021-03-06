package com.example.myapplication.jsonparse;

import java.io.Serializable;

public class Astronomy implements Serializable {
    private static final long serialversionUID = 129348933456L;
    String sunrise;
    String sunset;

    public Astronomy() {
    }

    public Astronomy(String sunrise, String sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        return "Astronomy{" +
                "sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                '}';
    }
}
