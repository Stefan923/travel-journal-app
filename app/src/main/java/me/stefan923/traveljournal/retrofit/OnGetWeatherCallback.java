package me.stefan923.traveljournal.retrofit;

import me.stefan923.traveljournal.model.Weather;

public interface OnGetWeatherCallback {

    void onSuccess(Weather weather);

    void onError();

}
