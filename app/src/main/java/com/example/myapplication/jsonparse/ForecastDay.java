package com.example.myapplication.jsonparse;

import java.io.Serializable;

public class ForecastDay implements Serializable {
    private static final long serialversionUID = 12934893544L;
    String day;
    Long date;
    Double low;
    Double high;
    String text;
    Integer code;

    public ForecastDay() {
    }

    public ForecastDay(String day, Long date, Double low, Double high, String text, Integer code) {
        this.day = day;
        this.date = date;
        this.low = low;
        this.high = high;
        this.text = text;
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ForecastDay{" +
                "day='" + day + '\'' +
                ", date=" + date +
                ", low=" + low +
                ", high=" + high +
                ", text='" + text + '\'' +
                ", code=" + code +
                '}';
    }
}
