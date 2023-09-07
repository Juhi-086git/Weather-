package com.mitkooo.weather.services;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

//used for writing and reading from the Shared preferences
public class DataService {

    private static DataService instance;
    private boolean isFahrenheit;
    private int city;
    private Context context;

    //make the class singleton
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    //constructor
    public DataService() {
        isFahrenheit = false;
        city = 0;
    }

    public void setContext(Context context) {
        this.context = context;
        read();
    }

    public boolean isFahrenheit() {
        return isFahrenheit;
    }

    public void setFahrenheit(boolean fahrenheit) {
        isFahrenheit = fahrenheit;
        write();
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
        write();
    }

    //write all the data to Shared preferences
    private void write() {
        SharedPreferences.Editor editor = this.context.getSharedPreferences("MyPreference", MODE_PRIVATE).edit();
        editor.putBoolean("isFahrenheit", isFahrenheit);
        editor.putInt("city", city);
        editor.commit();
    }

    //load all the data from Shared preferences
    private void read() {
        SharedPreferences prefs = this.context.getSharedPreferences("MyPreference", MODE_PRIVATE);
        isFahrenheit = prefs.getBoolean("isFahrenheit",false);
        city = prefs.getInt("city", 0);
    }
}
