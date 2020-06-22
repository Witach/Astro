package com.example.myapplication.contract;

import android.provider.BaseColumns;

public class LocationContract {
    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "locationList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_WOEID = "woeid";
        public static final String COLUMN_LAT = "lat";
        public static final String COLUMN_LON = "lon";

        private LocationEntry() {
        }
    }
}
