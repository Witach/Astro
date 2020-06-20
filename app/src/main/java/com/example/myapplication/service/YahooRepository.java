package com.example.myapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.jsonparse.YahooResponse;

import java.time.LocalDateTime;

public class YahooRepository {
    YahooResponse yahooResponse;
    LocalDateTime localDateTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    YahooRepository() {
        localDateTime = LocalDateTime.now();
    }

    public YahooResponse getYahooResponse() {
        return yahooResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public synchronized void setYahooResponse(YahooResponse yahooResponse) {
        this.yahooResponse = yahooResponse;
        localDateTime = LocalDateTime.now();
    }

    public synchronized void persist(){
        // TODO zrób zapiosanie do pamięci
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
