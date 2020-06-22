package com.example.myapplication.contract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.myapplication.contract.LocationContract.*;
import com.example.myapplication.jsonparse.Location;

public class LocationDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "locations.db";
    public static final int DATABASE_VERSION = 1;

    public LocationDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATIONLIST_TABLE = "CREATE TABLE "+
                LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LocationEntry.COLUMN_LAT + " REAL, " +
                LocationEntry.COLUMN_LON + " REAL, " +
                LocationEntry.COLUMN_NAME + " TEXT, " +
                LocationEntry.COLUMN_WOEID + " INTEGER " +
                ");";
        db.execSQL(SQL_CREATE_LOCATIONLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
