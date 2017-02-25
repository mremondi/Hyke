package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cbbhackscolby.hyke.R;

/**
 * Created by mremondi on 2/25/17.
 */

public class JoinGroupFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.join_group_fragment, null, false);

        final EditText joinGroupPasscode = (EditText) rootView.findViewById(R.id.join_group_passcode);
        Button btnJoinGroup = (Button) rootView.findViewById(R.id.btnJoinGroup);
        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check to see if the group exists, if it does append the current uid to the list of uids in the group
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                String uid = auth.getCurrentUser().getUid();
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("groups");
//                if (ref.child(joinGroupPasscode.getText().toString())!= null){
//                    ref.child(joinGroupPasscode.getText().toString()).;
//                }


            }
        });

        return rootView;
    }
}
