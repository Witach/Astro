package com.example.myapplication.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class YahooClient {
    OkHttpClient client;
    YahooService yahooService;

    public YahooClient(OkHttpClient client, YahooService yahooService) {
        this.client = client;
        this.yahooService = yahooService;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendForecastRequest(String city, Callback callback) {
        try {
            List<String> paraneters = new LinkedList<>();
            paraneters.add("location=" + URLEncoder.encode(city, "UTF-8"));
            Request request = yahooService.getRequest(paraneters, "/forecastrss");
            System.out.println(request.url().toString());
            sendRequest(request,callback);

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

     synchronized private void sendRequest(Request request, Callback callback){
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
