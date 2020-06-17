package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

public class AstroInfoActivity extends AppCompatActivity implements SunFragment.OnFragmentInteractionListener, MoonFragment.OnFragmentInteractionListener {

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SunFragment sunFragment;
    private MoonFragment moonFragment;
    private TextView longText;
    private TextView lat;
    private TextView timer;
    private Thread thread;

    SharedPreferences sharedPreferences;
    volatile boolean stopThread = false;
    BroadcastReceiver astroReceiver;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Astro-Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_astro_info);
        bindViewWithAttr();
        if (!getResources().getBoolean(R.bool.isTablet)) {
            setUpPagerView();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(sunFragment == null)
                sunFragment = new SunFragment();
            if(moonFragment == null)
                moonFragment = new MoonFragment();
            fragmentTransaction.replace(R.id.sunFrame, sunFragment, "moon_fragment");
            fragmentTransaction.replace(R.id.moonFrame, moonFragment, "sun_fragment");
            fragmentTransaction.commit();
        }
        loadPreferences();
        initThread();
        astroReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                execudeFragmentsProcedures();
            }
        };
        IntentFilter intentFilter = new IntentFilter("MyAction");
        registerReceiver(astroReceiver, intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void execudeFragmentsProcedures() {
        sunFragment.showSth();
        moonFragment.showSth();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        stopThread = false;
        if (thread != null)
            initThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopThread = true;
        unregisterReceiver(astroReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initThread() {
        thread = new Thread(() -> {
            try {
                while (!stopThread) {
                    refreshTimer();

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void bindViewWithAttr() {
        longText = (TextView) findViewById(R.id.longId);
        lat = (TextView) findViewById(R.id.latId);
        timer = (TextView) findViewById(R.id.timer);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        lat.setText("Lat: " + sharedPreferences.getString("lat", "15"));
        longText.setText("Long: " + sharedPreferences.getString("long", "20"));
        refreshTimer();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshTimer() {
        timer.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpPagerView() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        Stream.of("Sun", "Moon").forEach(val -> tabLayout.addTab(tabLayout.newTab().setText(val)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPageAdapter(viewPager);
        tabLayout.setOnTabSelectedListener(getOnTabSelectredListenerInstance());
    }

    private TabLayout.OnTabSelectedListener getOnTabSelectredListenerInstance() {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    private void setupViewPageAdapter(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if (sunFragment == null)
            sunFragment = new SunFragment();
        if (moonFragment == null)
            moonFragment = new MoonFragment();
        pagerAdapter.addFragment(sunFragment);
        pagerAdapter.addFragment(moonFragment);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
