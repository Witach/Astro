package com.example.myapplication.datarefresh;

import java.util.List;

public interface FragmentList {
    List<RefreshableFragment> getFragments();
    List<String> getFragmentsNames();
}
