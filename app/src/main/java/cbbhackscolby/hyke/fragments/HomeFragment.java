package cbbhackscolby.hyke.fragments;

import android.content.Intent;
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

import cbbhackscolby.hyke.LoginActivity;
import com.bumptech.glide.Glide;
import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.Main;
import cbbhackscolby.hyke.models.Weather;
import cbbhackscolby.hyke.models.WeatherJSON;
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
        final TextView tvWeatherLocation = (TextView) rootView.findViewById(R.id.tvWeatherLocation);
        final ImageView ivWeatherIcon = (ImageView) rootView.findViewById(R.id.ivWeatherIcon);

        Button sigin = (Button) rootView.findViewById(R.id.signin);
        sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final String city = "Boston";
        final Call<WeatherJSON> weatherQuery =  retrofit.create(WeatherAPI.class).getWeatherByLocation(city, "imperial", "3d3df0ff882e30f369c028263c36ed31");
        weatherQuery.enqueue(new Callback<WeatherJSON>() {
            @Override
            public void onResponse(Call<WeatherJSON> call, Response<WeatherJSON> response) {
                // need to cast Main to Main and get the temperature
                tvWeatherLocation.setText(city);
                tvTemperature.setText(response.body().getMain().getTemp().toString());
                String iconCode = response.body().getWeather().getIcon() + ".png";
                Log.d("HER", iconCode);
                Glide.with(rootView.getContext()).load("http://openweathermap.org/img/w/" + iconCode).into(ivWeatherIcon);
            }

            @Override
            public void onFailure(Call<WeatherJSON> call, Throwable t) {
                Log.d("error", t.getMessage());
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
