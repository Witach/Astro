package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;
import com.example.myapplication.jsonparse.YahooResponse;

import static com.example.myapplication.AstroFactory.*;
import static com.example.myapplication.AstroFactory.cutUselessInfo;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SunFragment extends RefreshableFragment {
        TextView sunrise;
        TextView sunset;
        TextView twilight_evening;
        TextView twilight_morning;
        private AstroCalculator astroCalculator;
        double lat, longVal;
    LayoutInflater inflater;
    ViewGroup container;

        int i = 1;
    public SunFragment() {
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataAttach(YahooResponse object) {
        showSth();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showSth(){
        initialGeoPosition();
        initialAstroCalculator();
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();
        String sunrise = sunInfo.getSunrise().toString();
        String sunset = sunInfo.getSunset().toString();
        String twilight_evening = sunInfo.getTwilightEvening().toString();
        String twilight_morning = sunInfo.getTwilightMorning().toString();
        this.sunrise.setText(cutUselessInfo(sunrise) + "\n Azimuth: " + sunInfo.getAzimuthRise());
        this.sunset.setText(cutUselessInfo(sunset) + "\n Azimuth: " + sunInfo.getAzimuthSet());
        this.twilight_evening.setText(cutUselessInfo(twilight_evening));
        this.twilight_morning.setText(cutUselessInfo(twilight_morning));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void initialAstroCalculator() {
        astroCalculator = astroCalculatorInstance(lat, longVal);
    }

    void initialGeoPosition() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        lat = Double.parseDouble(sharedPreferences.getString("lat", "15"));
        longVal = Double.parseDouble(sharedPreferences.getString("long", "20"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        ViewGroup viewGroup = initUserInterface();
        return viewGroup;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    ViewGroup initUserInterface( ){
        ViewGroup viewGroup = null;
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sun, container, false);
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sun_horizontal, container, false);
        sunrise = viewGroup.findViewById(R.id.sunrise);
        sunset = viewGroup.findViewById(R.id.sunset);
        twilight_evening = viewGroup.findViewById(R.id.twilight_evening);
        twilight_morning = viewGroup.findViewById(R.id.twilight_morning);
        showSth();
        return viewGroup;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        View view = initUserInterface();
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
        super.onConfigurationChanged(newConfig);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
