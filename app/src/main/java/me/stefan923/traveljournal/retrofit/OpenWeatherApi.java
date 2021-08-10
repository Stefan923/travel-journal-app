package me.stefan923.traveljournal.retrofit;

import me.stefan923.traveljournal.model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("data/2.5/weather?")
    Call<Weather> getWeather(@Query("q") String city,
                             @Query("APPID") String key);

}
