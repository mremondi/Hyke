package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cbbhackscolby.hyke.R;

/**
 * Created by mremondi on 2/25/17.
 */

public class DistressFragment extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    protected Boolean dist = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String uid = auth.getCurrentUser().getUid();
        final DatabaseReference ref = database.getReference("users").child(uid).child("distress");
        ref.setValue(dist);
        final View rootView = inflater.inflate(R.layout.fragment_distress, null, false);

        final TextView tvDistress = (TextView) rootView.findViewById(R.id.tvDistress);
        ImageView ivDistressSignal = (ImageView) rootView.findViewById(R.id.ivDistressSignal);
        ivDistressSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dist) {
                    ref.setValue(false);
                    dist = false;
                    tvDistress.setText("All Clear");
                }
                else {
                    ref.setValue(true);
                    dist = true;
                    tvDistress.setText("Group Notified");
                }
            }
        });
        return rootView;
    }
}