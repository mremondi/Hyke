package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cbbhackscolby.hyke.R;

/**
 * Created by mremondi on 2/25/17.
 */

public class CreateGroupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.create_group_fragment, null, false);

        final TextView mTextView = (TextView) rootView.findViewById(R.id.text);

        // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this.getContext());
                String url ="http://cpmajgaard.com:8081";

        // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                mTextView.setText("Secret Phrase: \n"+ response);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("HERE", "Error: " + error.networkResponse.statusCode);
                        mTextView.setText("That didn't work!");
                    }
                });
                 // Add the request to the RequestQueue.
                queue.add(stringRequest);

        return rootView;
    }

}
