package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.User;

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
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String uid = auth.getCurrentUser().getUid();
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("groups");
                
                ref.child(joinGroupPasscode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            Toast.makeText(getContext(), "That group does not exist", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            ref.getParent().child("users").child(uid).child("currGroup").setValue(joinGroupPasscode.getText().toString());

                            ref.child(joinGroupPasscode.getText().toString())
                                    .child("members")
                                    .child(uid)
                                    .setValue(true);

                            Toast.makeText(getContext(), "Joined Group Successfully", Toast.LENGTH_LONG).show();
                            Fragment fragment = new HomeFragment();
                            FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                            trans.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            trans.replace(R.id.content_frame, fragment, "tag");
                            trans.addToBackStack(null);
                            trans.commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if (ref.child(joinGroupPasscode.getText().toString())!= null){

                }


            }
        });

        return rootView;
    }
}
