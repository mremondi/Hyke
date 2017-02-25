package cbbhackscolby.hyke.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cbbhackscolby.hyke.R;

public class HomeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.home_fragment, null, false);

        TextView tvTemperature = (TextView) rootView.findViewById(R.id.tvTemperature);
        ImageView ivHomeImage = (ImageView) rootView.findViewById(R.id.ivHomeImage);

        return rootView;
    }
}
