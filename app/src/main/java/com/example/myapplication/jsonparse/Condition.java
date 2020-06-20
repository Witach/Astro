package com.example.myapplication.jsonparse;

public class Condition {

    String text;
    Double temperature;
    Double code;

    public Condition() {
    }

    public Condition(String text, Double temperature, Double code) {
        this.text = text;
        this.temperature = temperature;
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "text='" + text + '\'' +
                ", temperature=" + temperature +
                ", code=" + code +
                '}';
    }
}
