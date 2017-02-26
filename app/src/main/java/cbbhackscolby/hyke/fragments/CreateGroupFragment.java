package cbbhackscolby.hyke.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final Button btnCreateGroupCode = (Button) rootView.findViewById(R.id.btnCreateGroupCode);
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
                    public void onResponse(Call<String> call, final retrofit2.Response<String> response) {
                        final String groupName = response.body();
                        tvCode.setText(groupName);

                        btnCreateGroupCode.setText("Join Group");
                        btnCreateGroupCode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // Create the group
                                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("groups");
                                ref.setValue(response.body()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // add myself to the group
                                        ref.child(groupName).child("members").setValue(uid);
                                    }
                                });

                                // remove myself from the other group UH OH

                                // change my currGroup field
                                FirebaseDatabase.getInstance()
                                        .getReference("users")
                                        .child(uid)
                                        .child("currGroup")
                                        .setValue(groupName);

                                // add group name to group_locations
                                FirebaseDatabase.getInstance().getReference("group_locations").setValue(groupName);

                                Fragment fragment = new MessagesFragment();
                                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                                trans.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                trans.replace(R.id.content_frame, fragment, "tag");
                                trans.addToBackStack(null);
                                trans.commit();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("error", t.getMessage());
                    }
                });
            }
        });


        return rootView;
    }

}
