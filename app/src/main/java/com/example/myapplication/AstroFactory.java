package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.time.LocalDateTime;

public class AstroFactory {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AstroCalculator astroCalculatorInstance(double lat,double longVal){
        return new AstroCalculator(
                astroDateTimeInstance(lat, longVal),
                new AstroCalculator.Location(longVal,lat)
        );
    }

    public static int calculateTimeZone(double lat,double longVal){
        if (longVal >= 15. || longVal <= -15.)
            return (int) Math.floor((longVal + 15.) / 30.);
        else
            return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AstroDateTime astroDateTimeInstance(double lat, double longVal){
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AstroDateTime(
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour(),
                localDateTime.getMinute(),
                localDateTime.getSecond(),
                calculateTimeZone(lat,longVal),
                false
        );
    }

    public static String cutUselessInfo(String info){
        return  info.substring(0,info.length() - 6);
    }
}
