package com.example.myapplication.datarefresh;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.activity.AstroInfoActivity;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.YahooClient;
import com.example.myapplication.service.YahooRepository;
import com.example.myapplication.jsonparse.YahooResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IntervalRefresher extends TimerTask {
    List<RefreshableFragment> refreshableFragmentList;
    AstroInfoActivity activity;
    YahooClient yahooClient;
    YahooRepository yahooRepository;
    long refreshTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public IntervalRefresher(List<RefreshableFragment> refreshableFragmentList, AstroInfoActivity astroInfoActivity, long refreshTime) {
        this.refreshableFragmentList = refreshableFragmentList;
        this.activity = astroInfoActivity;
        DIManager diManager = DIManager.getInstance();
        yahooClient = diManager.getYahooClient();
        yahooRepository = diManager.getYahooRepository();
        this.refreshTime = refreshTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        yahooClient.sendForecastRequest("Sieradz", callback());
    }

    public Callback callback() {
        return new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (ChronoUnit.MINUTES.between(yahooRepository.getLocalDateTime(), LocalDateTime.now()) > -1) {
                    yahooRepository.setYahooResponse(response.body().string());
                    activity.runOnUiThread(() -> {
                        refreshableFragmentList.forEach(DataAttach::dataAttach);
                    });
                }
            }

            ;
        };
    }

}
