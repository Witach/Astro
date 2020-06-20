package com.example.myapplication.activity.pager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.datarefresh.FragmentList;

public abstract class MyFragmentPagerAdapter extends FragmentPagerAdapter implements FragmentList {
    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
}
