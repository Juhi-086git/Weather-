package com.mitkooo.weather.services;

import com.mitkooo.weather.models.WeatherModel;
import com.mitkooo.weather.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

//The Parser is used to transform the returned data from the request in easy to use format
public class Parser {

    public static ArrayList<WeatherModel> weeklyRequest(String jsonString) {
        //if the http request is successful, but no data is received, return null
        if (jsonString == null) {
            return null;
        }
        //prepare the result array
        ArrayList<WeatherModel> result = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);


            JSONArray jsonTime = jsonObject.getJSONObject("daily").getJSONArray("time");
            JSONArray jsonMaxTemp = jsonObject.getJSONObject("daily").getJSONArray("temperature_2m_max");
            JSONArray jsonMinTemp = jsonObject.getJSONObject("daily").getJSONArray("temperature_2m_min");

            JSONObject jsonDailyUnits = (JSONObject) jsonObject.get("daily_units");
            String unit = jsonDailyUnits.getString("temperature_2m_min");

            // get current temperature details
            JSONObject jsonCurrentWeather = (JSONObject) jsonObject.get("current_weather");
            WeatherModel currentWeather = new WeatherModel();

            currentWeather.setCurrentTemp(jsonCurrentWeather.getInt("temperature"));
            currentWeather.setMinTemp(jsonMinTemp.getDouble(0));
            currentWeather.setMaxTemp(jsonMaxTemp.getDouble(0));
            currentWeather.setDate(DateUtils.stringToDate(jsonTime.getString(0)));
            currentWeather.setDay(jsonCurrentWeather.getInt("is_day") == 1);
            currentWeather.setTemperatureUnit(unit);

            result.add(currentWeather);

            //iterate through the rest of the results
            for (int i = 1; i < jsonTime.length(); i++) {
                WeatherModel weatherModel = new WeatherModel();
                weatherModel.setMinTemp(jsonMinTemp.getDouble(i));
                weatherModel.setMaxTemp(jsonMaxTemp.getDouble(i));
                currentWeather.setTemperatureUnit(unit);

                Date date = DateUtils.stringToDate(jsonTime.getString(i));
                weatherModel.setDate(date);

                result.add(weatherModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

}
