package com.example.myapplication.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.contract.LocationContract;
import com.example.myapplication.contract.LocationDBHelper;
import com.example.myapplication.jsonparse.Location;
import com.example.myapplication.jsonparse.YahooResponse;
import com.example.myapplication.service.DIManager;
import com.example.myapplication.service.LocationException;
import com.example.myapplication.service.YahooClient;
import com.example.myapplication.service.YahooRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.example.myapplication.contract.LocationContract.*;

public class LocationActivity extends AppCompatActivity {

    Spinner spinner;
    TextInputEditText lon;
    TextInputEditText lat;
    YahooClient yahooClient;
    List<String> locationList = new ArrayList<>();
    List<Integer> listOfWoeids = new ArrayList<>();
    RelativeLayout frameLayout;
    SQLiteDatabase sqLiteDatabase;
    YahooRepository yahooRepository;
    Gson gson = new Gson();
    boolean isNothinSelected = true;
    private ConnectivityManager connectivityManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        connectivityManager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        spinner = findViewById(R.id.location_spinner);
        lon = findViewById(R.id.long_input);
        lat = findViewById(R.id.lat_input);
        LocationDBHelper locationDBHelper = new LocationDBHelper(this);
        sqLiteDatabase = locationDBHelper.getWritableDatabase();
        loadAllRecordsFromDb();
        DIManager diManager = DIManager.getInstance();
        yahooClient = diManager.getYahooClient();
        yahooRepository = diManager.getYahooRepository();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locationList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                System.out.println("Ustawiono: " + locationList.get(position));
                if (!isNothinSelected) {
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("woeid", Long.toString(listOfWoeids.get(position))).commit();
                } else {
                    isNothinSelected = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSaveLocationClick(View view) {
        String lonString = lon.getText().toString();
        String latString = lat.getText().toString();
        try {
            double lon = Double.parseDouble(lonString);
            double lat = Double.parseDouble(latString);
            if (lon > 180 || lat > 180 || lat < 0 || lon < 0)
                throw new LocationException();

            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                yahooClient.sendForecastRequest(lat, lon, callback());
            } else {
                Toast.makeText(getApplicationContext(), "Brak połączenia", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Dane nie są liczbą", Toast.LENGTH_SHORT).show();
        } catch (LocationException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private Callback callback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Błędne współrzędne", Toast.LENGTH_SHORT).show();
                });
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                YahooResponse yahooResponse = gson.fromJson(response.body().string(), YahooResponse.class);
                if (locationList.contains(yahooResponse.getLocation().getCity())) {
                    runOnUiThread(()->Toast.makeText(getApplicationContext(), "Lokalizacja już istnieje", Toast.LENGTH_SHORT).show());
                } if(yahooResponse.getLocation().getWoeid() == null){
                    runOnUiThread(()->Toast.makeText(getApplicationContext(), "Błędna lokalizacja", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> {
                        lon.setText("");
                        lat.setText("");
                        Toast.makeText(getApplicationContext(), "Dodano lokalizację", Toast.LENGTH_SHORT).show();
                        locationList.add(yahooResponse.getLocation().getCity());
                        System.out.println(yahooResponse);
                        listOfWoeids.add(yahooResponse.getLocation().getWoeid().intValue());
                        putIntoDatabase(yahooResponse.getLocation());
                    });
                }
            }
        };
    }

    public void loadAllRecordsFromDb() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + LocationEntry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_NAME));
                int woeid = cursor.getInt(cursor.getColumnIndex(LocationEntry.COLUMN_WOEID));
                locationList.add(name);
                listOfWoeids.add(woeid);
                cursor.moveToNext();
            }
        }

    }

    public void putIntoDatabase(Location location){
        ContentValues cv = new ContentValues();
        cv.put(LocationEntry.COLUMN_NAME,location.getCity());
        cv.put(LocationEntry.COLUMN_LAT,location.getLat());
        cv.put(LocationEntry.COLUMN_LON,location.getLog());
        cv.put(LocationEntry.COLUMN_WOEID,location.getWoeid());
        sqLiteDatabase.insert(LocationEntry.TABLE_NAME,null,cv);
    }
}
