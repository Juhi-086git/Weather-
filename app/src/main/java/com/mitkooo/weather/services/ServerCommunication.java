package com.mitkooo.weather.services;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mitkooo.weather.listeners.RequestsListener;
import com.mitkooo.weather.utils.DateUtils;

import cz.msebera.android.httpclient.Header;

//ServerCommunication is used for executing building and executing requests
public class ServerCommunication {

    private static ServerCommunication instance;
    private final AsyncHttpClient client;
    private RequestsListener listener;
    public void setListener(RequestsListener listener) {
        this.listener = listener;
    }

    //make the class singleton
    public static ServerCommunication getInstance(Context context) {
        if (instance == null) {
            instance = new ServerCommunication();
        }

        return instance;
    }

    //constructor
    public ServerCommunication() {
        this.client = new AsyncHttpClient();
    }

    public void requestForecast() {
//        String ORIGINAL_REQUEST = "https://api.open-meteo.com/v1/forecast?latitude=42.70&longitude=23.32&daily=temperature_2m_max,temperature_2m_min&current_weather=true&start_date=2023-04-07&end_date=2023-04-14&timezone=Europe%2FBerlin";

        String requestUrl = "https://api.open-meteo.com/v1/forecast?daily=temperature_2m_max,temperature_2m_min&current_weather=true&timezone=Europe%2FBerlin";
        String dateParams = String.format("&start_date=%s&end_date=%s", DateUtils.getCurrentDateToStringForURL(), DateUtils.getNextWeekDateToStringForURL());

        String r;
        if (DataService.getInstance().isFahrenheit()) {
            r = String.format("%s%s%s&temperature_unit=fahrenheit", requestUrl, dateParams, getLocation());
        } else {
            r = String.format("%s%s%s", requestUrl, dateParams, getLocation());
        }

        client.get(r, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //transform the bytes to JSON format
                String jsonString = new String(responseBody);
                Log.e("RESULT", jsonString);
                //Send the result to the Parser
                listener.weeklyRequestReceived(jsonString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.weeklyRequestReceived(null);
            }
        });
    }

    public void cancelAllRequests() {
        client.cancelAllRequests(true);
    }

    //set the coordinates for each city.
    private String getLocation() {
        String result;
        switch (DataService.getInstance().getCity()) {
            case 1:
                result = "&latitude=42.15&longitude=24.75";
                break;
            case 2:
                result = "&latitude=42.51&longitude=27.47";
                break;
            case 3:
                result = "&latitude=43.22&longitude=27.92";
                break;
            case 0:
            default:
                result = "&latitude=42.70&longitude=23.32";
        }
        return result;
    }

}
