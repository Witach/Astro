package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.jsonparse.YahooResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IntervalRefresher extends TimerTask {
    List<RefreshableFragment> refreshableFragmentList;
    OkHttpClient client;
    AstroInfoActivity activity;
    Gson gson;

    public IntervalRefresher(List<RefreshableFragment> refreshableFragmentList, AstroInfoActivity astroInfoActivity) {
        this.refreshableFragmentList = refreshableFragmentList;
        this.activity = astroInfoActivity;
        this.gson = new Gson();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        YahooService yahooService = new YahooService("dj0yJmk9QjduZk1TRVFMN250JmQ9WVdrOVJFNWlTazFFTkhFbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmc3Y9MCZ4PTdh",
                "48746667d1352b63b406a30f16edead0c0d47097",
                "https://weather-ydn-yql.media.yahoo.com/forecastrss",
                "DNbJMD4q");
        Request request = yahooService.getRequest("Sieradz");
        client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                YahooResponse yahooResponse = gson.fromJson(json, YahooResponse.class);
                System.out.println(yahooResponse);
                activity.runOnUiThread( () -> {
                    refreshableFragmentList.forEach(fragment -> fragment.dataAttach(yahooResponse));
                });
            };
        });
    }
}
