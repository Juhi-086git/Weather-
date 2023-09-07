package com.mitkooo.weather.models;

import com.mitkooo.weather.utils.DateUtils;

import java.util.Date;

public class WeatherModel {

    private Date date;
    private double minTemp;
    private double maxTemp;
    private double currentTemp;
    private boolean isDay;

    private String temperatureUnit;

    public WeatherModel() {
        date = new Date();
        minTemp = 0;
        maxTemp = 0;
        currentTemp = 0;
        isDay = true;
        temperatureUnit = "";
    }

    public Date getDate() {
        return date;
    }

    public String getDateToString() {
        return DateUtils.dateToString(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getMinTempString() {
        return String.format("L: %s%s", minTemp, temperatureUnit);
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getMaxTempString() {
        return String.format("H: %s%s", maxTemp, temperatureUnit);
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getCurrentTemp() {
        return getCurrentTemp();
    }

    public String getCurrentTempString() {
        return String.format("%s%s", currentTemp, temperatureUnit);
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

}

