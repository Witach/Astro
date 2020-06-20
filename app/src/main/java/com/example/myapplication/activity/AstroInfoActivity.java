package com.example.myapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.myapplication.activity.pager.MobilePagerAdapter;
import com.example.myapplication.activity.pager.MyFragmentPagerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.activity.pager.TabletPagerAdapter;
import com.example.myapplication.datarefresh.IntervalRefresher;
import com.example.myapplication.datarefresh.RefreshableFragment;
import com.example.myapplication.fragments.MoonFragment;
import com.example.myapplication.fragments.SunFragment;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AstroInfoActivity extends AppCompatActivity implements SunFragment.OnFragmentInteractionListener, MoonFragment.OnFragmentInteractionListener {

    private MyFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView longText;
    private TextView lat;
    private TextView timer;
    private Thread thread;
    private TimerTask timerTask;
    private Timer timerProc;
    private List<RefreshableFragment> fragments;

    SharedPreferences sharedPreferences;
    volatile boolean stopThread = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Astro-Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_astro_info);
        bindViewWithAttr();
        if (!getResources().getBoolean(R.bool.isTablet)) {
            setUpPagerViewForMobile();
        } else {
            setUpPagerViewForTablet();
        }
        loadPreferences();
        initThread();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpIntervalTask() {
        String interval_time = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("interval_time", "15");
        long intervl = Integer.parseInt(interval_time);
        long intervalValue = intervl * 60 * 1000;
        timerProc = new Timer();
        timerTask = new IntervalRefresher(fragments, this, intervalValue);
        timerProc.scheduleAtFixedRate(timerTask, 1000, intervalValue);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        stopThread = false;
        if (thread != null)
            initThread();
        setUpIntervalTask();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopThread = true;
        timerTask.cancel();
        timerProc.cancel();
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
        longText = findViewById(R.id.longId);
        lat = findViewById(R.id.latId);
        timer = findViewById(R.id.timer);
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
        this.runOnUiThread( () -> {
            timer.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpPagerViewForMobile() {
        if (fragments == null)
            setUpFragmentsForMobile();
        fragments = pagerAdapter.getFragments();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpPagerViewForTablet() {
        if (fragments == null)
            setUpFragmentsForTablet();
        fragments = pagerAdapter.getFragments();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpFragmentsForMobile() {
        fragments = new LinkedList<>();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        if (pagerAdapter == null)
            pagerAdapter = new MobilePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPageAdapter(viewPager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpFragmentsForTablet() {

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
        if (pagerAdapter == null)
            pagerAdapter = new TabletPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPageAdapter(viewPager);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViewPageAdapter(ViewPager viewPager) {
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
