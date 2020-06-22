package com.example.myapplication.jsonparse;

import android.content.res.Configuration;

import java.io.Serializable;

public class CurrentObservation implements Serializable {
    private static final long serialversionUID = 1573489324L;
    Wind wind;
    Atmosphere atmosphere;
    Astronomy astronomy;
    Condition condition;
    Long pubDate;


    public CurrentObservation() {
    }

    public CurrentObservation(Wind wind, Atmosphere atmosphere, Astronomy astronomy, Condition condition, Long pubDate) {
        this.wind = wind;
        this.atmosphere = atmosphere;
        this.astronomy = astronomy;
        this.condition = condition;
        this.pubDate = pubDate;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Astronomy getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(Astronomy astronomy) {
        this.astronomy = astronomy;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "CurrentObservation{" +
                "wind=" + wind +
                ", atmosphere=" + atmosphere +
                ", astronomy=" + astronomy +
                ", condition=" + condition +
                ", pubDate=" + pubDate +
                '}';
    }
}
