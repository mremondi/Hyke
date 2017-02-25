package cbbhackscolby.hyke.network;

import cbbhackscolby.hyke.models.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/25/17.
 */

public interface WeatherAPI {
    // 3d3df0ff882e30f369c028263c36ed31
    @GET("weather?{lat}&{lon}&APPID=3d3df0ff882e30f369c028263c36ed31")
    Call<Weather> getWeatherByLocation(@Path("lat") String lat, @Path("lon") String lon);
}
