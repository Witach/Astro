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

import static com.example.myapplication.AstroFactory.astroCalculatorInstance;
import static com.example.myapplication.AstroFactory.cutUselessInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoonFragment extends RefreshableFragment {
    TextView moonrise;
    TextView moonset;
    TextView new_moon;
    TextView full_moon;
    TextView moon_phase;
    TextView syndic_month;
    double lat;
    double longVal;
    LayoutInflater inflater;
    ViewGroup container;
    int i = 1;

    private AstroCalculator astroCalculator;

    public MoonFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataAttach(Object object) {
        showSth();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showSth() {
        initialGeoPosition();
        initialAstroCalculator();
        AstroCalculator.MoonInfo moonInfo = astroCalculator.getMoonInfo();
        String moonRise = moonInfo.getMoonrise().toString();
        String moonsetStr = moonInfo.getMoonset().toString();
        String new_moon = moonInfo.getNextNewMoon().toString();
        String full_moon = moonInfo.getNextFullMoon().toString();
        String syndic_month = Double.toString(moonInfo.getAge());
        moonrise.setText(cutUselessInfo(moonRise));
        moonset.setText(cutUselessInfo(moonsetStr));
        this.new_moon.setText(cutUselessInfo(new_moon));
        this.full_moon.setText(cutUselessInfo(full_moon));
        this.moon_phase.setText(Double.toString(moonInfo.getIllumination()));
        this.syndic_month.setText(syndic_month);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        this.container = container;
        ViewGroup viewGroup = initUserUI();
        return viewGroup;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    ViewGroup initUserUI(){
        ViewGroup view = null;
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            view = (ViewGroup) inflater.inflate(R.layout.fragment_moon, container, false);
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            view = (ViewGroup) inflater.inflate(R.layout.fragment_moon_horizontal, container, false);
        moonrise = view.findViewById(R.id.moonrise);
        moonset = view.findViewById(R.id.moonset);
        moon_phase = view.findViewById(R.id.moon_phase);
        full_moon = view.findViewById(R.id.full_moon);
        new_moon = view.findViewById(R.id.new_moon);
        syndic_month = view.findViewById(R.id.syndic_month);
        showSth();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void initialAstroCalculator() {
        astroCalculator = astroCalculatorInstance(lat, longVal);
    }

    void initialGeoPosition() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        lat = Double.parseDouble(sharedPreferences.getString("lat", "15"));
        longVal = Double.parseDouble(sharedPreferences.getString("long", "20"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        View view = initUserUI();
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(view);
        super.onConfigurationChanged(newConfig);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
