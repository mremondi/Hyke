package cbbhackscolby.hyke.network;

import cbbhackscolby.hyke.models.Weather;
import cbbhackscolby.hyke.models.WeatherJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/25/17.
 */

public interface WeatherAPI {
    @GET("weather")
    Call<WeatherJSON> getWeatherByLocation(@Query("q") String city, @Query("units") String units, @Query("appid") String id);
}
