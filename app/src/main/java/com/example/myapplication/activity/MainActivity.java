package com.example.myapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.contract.LocationDBHelper;
import com.example.myapplication.jsonparse.YahooResponse;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.Units;
import com.example.myapplication.service.YahooClient;
import com.example.myapplication.service.YahooDataFormatter;
import com.example.myapplication.service.YahooRepository;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    YahooClient yahooClient;
    YahooRepository yahooRepository;
    YahooDataFormatter yahooDataFormatter;
    ConnectivityManager connectivityManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DIManager diManager = DIManager.getInstance();
        yahooClient = diManager.getYahooClient();
        yahooClient.setContext(getApplicationContext());
        yahooRepository = diManager.getYahooRepository();
        yahooRepository.setContext(getApplicationContext());
        connectivityManager = (ConnectivityManager)getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Optional<YahooRepository> yahooRepositoryOptional = yahooRepository.load();
        if(yahooRepositoryOptional.isPresent()){
            YahooRepository loadedYahooRepository = yahooRepositoryOptional.get();
            yahooRepository.setYahooResponse(loadedYahooRepository.getYahooResponse());
            yahooRepository.setLocalDateTime(loadedYahooRepository.getLocalDateTime());
        }
        String unitType = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("unit_type", Units.F.name());
        String woeidString = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("woeid", "485483");
        long woeid = Long.parseLong(woeidString);
        yahooClient.setWoeid(woeid);
        boolean isConfigChanged = false;
        if (yahooRepository.getYahooResponse() !=null && yahooRepository.getYahooResponse().getLocation().getWoeid() != woeid){
            isConfigChanged = true;
        }

        Units unit = Units.valueOf(unitType);
        if(unit != yahooClient.getUnits()){
            isConfigChanged = true;
        }
        yahooClient.setUnits(unit);
        yahooDataFormatter = diManager.getYahooDataFormatter();
        yahooDataFormatter.setSelectedUnitsType(unit);
        String interval = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("interval_time", "15");
        Integer intervalVal = Integer.parseInt(interval);

        if((yahooRepository.getYahooResponse() == null || ChronoUnit.MINUTES.between(yahooRepository.getLocalDateTime(), LocalDateTime.now()) > intervalVal || isConfigChanged) && isConnected){
            yahooClient.sendForecastRequestWithWoeid(callback());
        } else if(!isConnected){
            Toast.makeText(getApplicationContext(),"Brak połączenia",Toast.LENGTH_SHORT).show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onPostResume() {
        super.onPostResume();
        String unitType = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("unit_type", Units.F.name());
        Units unit = Units.valueOf(unitType);
        yahooDataFormatter.setSelectedUnitsType(unit);
        String woeidString = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("woeid", "485483");
        long woeid = Long.parseLong(woeidString);
        boolean isConfigChanged = false;
        if (yahooRepository.getYahooResponse() !=null && yahooRepository.getYahooResponse().getLocation().getWoeid() != woeid){
            isConfigChanged = true;
        }
        if(unit != yahooClient.getUnits()){
            isConfigChanged = true;
        }
        yahooClient.setUnits(unit);
        yahooClient.setWoeid(woeid);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if((yahooRepository.getYahooResponse() == null ||  isConfigChanged) && isConnected){
            yahooClient.sendForecastRequestWithWoeid(callback());
        } else if(!isConnected){
            Toast.makeText(getApplicationContext(),"Brak połączenia",Toast.LENGTH_SHORT).show();
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

    public void goToLocationsActivity(View view){
        Intent intent = new Intent(this, LocationActivity.class);
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
                yahooRepository.setYahooResponse(response.body().string());
                yahooRepository.persist();
//                System.out.println(response.body().string());
            }
        };
    }




}
