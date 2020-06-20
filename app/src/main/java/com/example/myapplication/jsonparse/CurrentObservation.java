package com.example.myapplication.jsonparse;

import android.content.res.Configuration;

public class CurrentObservation {
    Wind wind;
    Atmosphere atmosphere;
    Astronomy astronomy;
    Condition condition;


    public CurrentObservation() {
    }

    public CurrentObservation(Wind wind, Atmosphere atmosphere, Astronomy astronomy, Condition condition) {
        this.wind = wind;
        this.atmosphere = atmosphere;
        this.astronomy = astronomy;
        this.condition = condition;
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

    @Override
    public String toString() {
        return "CurrentObservation{" +
                "wind=" + wind +
                ", atmosphere=" + atmosphere +
                ", astronomy=" + astronomy +
                ", condition=" + condition +
                '}';
    }
}
