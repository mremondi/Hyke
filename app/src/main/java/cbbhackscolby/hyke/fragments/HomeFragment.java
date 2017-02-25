package cbbhackscolby.hyke.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.Main;
import cbbhackscolby.hyke.models.Weather;
import cbbhackscolby.hyke.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_fragment, null, false);

        final TextView tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        TextView tvWeatherLocation = (TextView) rootView.findViewById(R.id.tvWeatherLocation);

        Button sigin = (Button) rootView.findViewById(R.id.signin);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Call<Weather> weatherQuery =  retrofit.create(WeatherAPI.class).getWeatherByLocation("139", "35");
        weatherQuery.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                // need to cast Main to Main and get the temperature
                tvTemperature.setText(response.body().getMain());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });


        Button bHomeButton = (Button) rootView.findViewById(R.id.bHomeButton);
        bHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HERE","Testing");
            }
        });
        Animation pulse = AnimationUtils.loadAnimation(getContext(),R.anim.pulse);
        bHomeButton.startAnimation(pulse);

        return rootView;
    }

}
