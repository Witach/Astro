package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.TimerTask;

public class IntervalRefresher extends TimerTask {
    List<RefreshableFragment> refreshableFragmentList;

    public IntervalRefresher(List<RefreshableFragment> refreshableFragmentList) {
        this.refreshableFragmentList = refreshableFragmentList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        refreshableFragmentList.forEach(fragment -> fragment.dataAttach(null));
        YahooService.getInstance().doSth();
    }
}
