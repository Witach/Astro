package com.example.myapplication.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

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
    Units units;
    Context context;
    ConnectivityManager cm;

    public YahooClient(OkHttpClient client, YahooService yahooService, Context context) {
        this.client = client;
        this.yahooService = yahooService;
        this.context = context;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendForecastRequest(String city, Callback callback) {
        try {
            List<String> paraneters = new LinkedList<>();
            paraneters.add("location=" + URLEncoder.encode(city, "UTF-8"));
            paraneters.add("u=" + URLEncoder.encode(units.name().toLowerCase(), "UTF-8"));
            Request request = yahooService.getRequest(paraneters, "/forecastrss");
            System.out.println(request.url().toString());
            sendRequest(request,callback);

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

     synchronized private void sendRequest(Request request, Callback callback){
         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
         boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            Call call = client.newCall(request);
            call.enqueue(callback);
        } else {
            Toast.makeText(context, "Brak Połączenia", Toast.LENGTH_SHORT).show();
        }
    }

    public void setContext(Context context) {
        this.context = context;
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
