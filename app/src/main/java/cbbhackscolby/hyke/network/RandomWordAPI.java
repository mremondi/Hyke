package cbbhackscolby.hyke.network;

import cbbhackscolby.hyke.models.WeatherJSON;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/25/17.
 */

public interface RandomWordAPI {

    @GET("get.php")
    Call<String> getRandomWord();
}
