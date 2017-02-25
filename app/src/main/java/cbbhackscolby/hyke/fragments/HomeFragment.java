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

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_fragment, null, false);

        TextView tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
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
