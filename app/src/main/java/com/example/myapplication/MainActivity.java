package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Spinner;

import java.time.Instant;
import java.util.Calendar;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarm();

    }

    public void goToAstroInfoActivity(View view){
        Intent intent = new Intent(this, AstroInfoActivity.class);
        intent.putExtra("context","eloszka");
        startActivity(intent);
    }

    public void goToSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("context","eloszka");
        startActivity(intent);
    }


    public void setAlarm(){
        String interval_time = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("interval_time","15");
        long intervalValue = Integer.parseInt(interval_time) * 60;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("MyAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + intervalValue,intervalValue, pendingIntent);
    }


}
