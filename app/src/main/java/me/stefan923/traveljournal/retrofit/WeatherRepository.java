package me.stefan923.traveljournal.retrofit;

import androidx.annotation.NonNull;

import me.stefan923.traveljournal.BuildConfig;
import me.stefan923.traveljournal.model.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {

    private static final String APPID = "8024c2c8db1698a3283246cbd7e4784d";

    private static WeatherRepository weatherRepository;

    private final OpenWeatherApi openWeatherApi;

    private WeatherRepository(OpenWeatherApi openWeatherApi) {
        this.openWeatherApi = openWeatherApi;
    }

    public static WeatherRepository getInstance() {
        if (weatherRepository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.OPEN_WEATHER_MAP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherRepository = new WeatherRepository(retrofit.create(OpenWeatherApi.class));
        }

        return weatherRepository;
    }

    public void getWeather(final OnGetWeatherCallback callback, String city) {
        openWeatherApi.getWeather(city, APPID)
                .enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                        if (response.isSuccessful()) {
                            Weather weather = response.body();
                            if (weather != null) {
                                callback.onSuccess(weather);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        callback.onError();
                    }
                });
    }

}
