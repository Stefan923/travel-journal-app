package me.stefan923.traveljournal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("weather")
    private List<WeatherInfo> weatherInfo;

    public List<WeatherInfo> getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(List<WeatherInfo> weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

}
