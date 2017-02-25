package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cbbhackscolby.hyke.R;

/**
 * Created by mremondi on 2/25/17.
 */

public class DistressFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_distress, null, false);
        ImageView ivDistressSignal = (ImageView) rootView.findViewById(R.id.ivDistressSignal);
        ivDistressSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference("users").child("uid111").child("distress");
                ref.setValue(true);
            }
        });
        return rootView;
    }
}