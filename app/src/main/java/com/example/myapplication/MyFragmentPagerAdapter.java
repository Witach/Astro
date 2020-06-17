package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public abstract class MyFragmentPagerAdapter extends FragmentPagerAdapter implements FragmentList {
    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
}
