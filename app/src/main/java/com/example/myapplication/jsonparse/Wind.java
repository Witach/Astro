package com.example.myapplication.jsonparse;

public class Wind {
    Integer chill;
    Integer direction;
    Double speed;

    public Wind() {
    }

    public Wind(Integer chill, Integer direction, Double speed) {
        this.chill = chill;
        this.direction = direction;
        this.speed = speed;
    }

    public Integer getChill() {
        return chill;
    }

    public void setChill(Integer chill) {
        this.chill = chill;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "chill=" + chill +
                ", direction=" + direction +
                ", speed=" + speed +
                '}';
    }
}
