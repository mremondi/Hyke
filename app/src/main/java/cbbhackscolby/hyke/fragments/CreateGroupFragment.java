package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.WeatherJSON;
import cbbhackscolby.hyke.network.RandomWordAPI;
import cbbhackscolby.hyke.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by mremondi on 2/25/17.
 */

public class CreateGroupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group_fragment, null, false);

        final TextView tvCode = (TextView) rootView.findViewById(R.id.tvCode);

//        // Instantiate the RequestQueue.
//        final RequestQueue queue = Volley.newRequestQueue(this.getContext());
//        final String url ="http://cpmajgaard.com:8081";

        Button btnCreateGroupCode = (Button) rootView.findViewById(R.id.btnCreateGroupCode);
        btnCreateGroupCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.setgetgo.com/randomword/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
                final Call<String> randomWordQuery =  retrofit.create(RandomWordAPI.class).getRandomWord();
                randomWordQuery.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                        Log.d("WORD", response.body());
                        tvCode.setText(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });

                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                mTextView.setText("Secret Phrase: \n"+ response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d("HERE", "Error: " + error.networkResponse.statusCode);
//                        mTextView.setText("That didn't work!");
//                    }
//                });
//                // Add the request to the RequestQueue.
//                queue.add(stringRequest);
            }
        });


        return rootView;
    }

}
