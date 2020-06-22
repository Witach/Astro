package com.example.myapplication.service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.jsonparse.YahooResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

public class YahooRepository implements Serializable {
    private static final long serialversionUID = 1293489324L;

    YahooResponse yahooResponse;
    LocalDateTime localDateTime;
    transient Gson gson = new Gson();
    transient Context context;
    private static final String filename = "appState.data";

    public void setContext(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    YahooRepository() {
    }

    public YahooResponse getYahooResponse() {
        return yahooResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public synchronized void setYahooResponse(YahooResponse yahooResponse) {
        this.yahooResponse = yahooResponse;
        localDateTime = LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public synchronized void setYahooResponse(String json) {
        yahooResponse = gson.fromJson(json, YahooResponse.class);
        localDateTime = LocalDateTime.now();
    }

    public synchronized void persist() {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public synchronized Optional<YahooRepository> load() {
        Optional<YahooRepository> yahooRepository = Optional.empty();
        try (FileInputStream fis = context.openFileInput(filename)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            yahooRepository = Optional.ofNullable((YahooRepository) objectInputStream.readObject());
        } catch (FileNotFoundException e) {
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return yahooRepository;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
