package com.example.myapplication.jsonparse;

import com.google.gson.annotations.SerializedName;

public class Location {
    String city;
    String region;
    Long woeid;
    String country;
    Double lat;
    @SerializedName("long")
    Double log;
    String timezone_id;

    public Location() {
    }

    public Location(String city, String region, Long woeid, String country, Double lat, Double log, String timezone_id) {
        this.city = city;
        this.region = region;
        this.woeid = woeid;
        this.country = country;
        this.lat = lat;
        this.log = log;
        this.timezone_id = timezone_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getWoeid() {
        return woeid;
    }

    public void setWoeid(Long woeid) {
        this.woeid = woeid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLog() {
        return log;
    }

    public void setLog(Double log) {
        this.log = log;
    }

    public String getTimezone_id() {
        return timezone_id;
    }

    public void setTimezone_id(String timezone_id) {
        this.timezone_id = timezone_id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", woeid=" + woeid +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                ", timezone_id='" + timezone_id + '\'' +
                '}';
    }
}
