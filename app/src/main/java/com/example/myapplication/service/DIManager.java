package com.example.myapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import okhttp3.OkHttpClient;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DIManager {
    private static final  DIManager diManagerInstance = new DIManager();
    YahooService yahooService;
    OkHttpClient okHttpClient;
    YahooClient yahooClient;
    YahooRepository yahooRepository;
    private DIManager() {
        yahooService = new YahooService("dj0yJmk9QjduZk1TRVFMN250JmQ9WVdrOVJFNWlTazFFTkhFbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTdh",
                "48746667d1352b63b406a30f16edead0c0d47097",
                "DNbJMD4q");
        okHttpClient = new OkHttpClient();
        yahooClient = new YahooClient(okHttpClient,yahooService);
        yahooRepository = new YahooRepository();
    }

    public static DIManager getInstance(){
        return diManagerInstance;
    }

    public YahooClient getYahooClient() {
        return yahooClient;
    }

    public YahooRepository getYahooRepository() {
        return yahooRepository;
    }
}
