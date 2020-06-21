package com.example.myapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.jsonparse.YahooResponse;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.Units;
import com.example.myapplication.service.YahooClient;
import com.example.myapplication.service.YahooRepository;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    YahooClient yahooClient;
    YahooRepository yahooRepository;
    Gson gson;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DIManager diManager = DIManager.getInstance();
        gson = new Gson();
        yahooClient = diManager.getYahooClient();
        yahooRepository = diManager.getYahooRepository();
        String unitType = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("unit_type", Units.F.name());
        Units unit = Units.valueOf(unitType);
        yahooClient.setUnits(unit);
        diManager.getYahooDataFormatter().setSelectedUnitsType(unit);
        String interval = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("interval_time", "15");
        Integer intervalVal = Integer.parseInt(interval);
        if(ChronoUnit.MINUTES.between(yahooRepository.getLocalDateTime(), LocalDateTime.now()) > -1){
            yahooClient.sendForecastRequest("Sieradz",callback());
        }

    }

    public void goToAstroInfoActivity(View view){
        Intent intent = new Intent(this, AstroInfoActivity.class);
        startActivity(intent);
    }

    public void goToSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public Callback callback(){
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                YahooResponse yahooResponse = gson.fromJson(response.body().string(), YahooResponse.class);
                yahooRepository.setYahooResponse(yahooResponse);
                System.out.println(yahooResponse);
            }
        };
    }




}
