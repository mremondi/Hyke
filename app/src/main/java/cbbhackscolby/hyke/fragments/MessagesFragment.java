package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.Message;

public class MessagesFragment extends Fragment {

    private FirebaseListAdapter<Message> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages, null, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String uid = user.getUid();
        final String name = user.getDisplayName();
        DatabaseReference currentGroup = FirebaseDatabase.getInstance().getReference("users").child(uid).child("currGroup");

        currentGroup.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //Toast.makeText(getContext(), "That group does not exist", Toast.LENGTH_SHORT).show();
                } else {
                    final String currentGroup = (String) dataSnapshot.getValue();


                    ListView listOfMessages = (ListView) rootView.findViewById(R.id.list_of_messages);

                    adapter = new FirebaseListAdapter<Message>(getActivity(), Message.class,
                            R.layout.message, FirebaseDatabase.getInstance()
                            .getReference()
                            .child("groups")
                            .child(currentGroup)
                            .child("messages")) {
                        @Override
                        protected void populateView(View v, Message model, int position) {
                            // Get references to the views of message.xml
                            TextView messageText = (TextView) v.findViewById(R.id.message_text);
                            TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                            TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                            // Set their text
                            messageText.setText(model.message);
                            messageUser.setText(model.origin);

                            // Format the date before showing it
                            messageTime.setText(model.dtime);
                        }
                    };
                    listOfMessages.setAdapter(adapter);

                    FloatingActionButton fabSend = (FloatingActionButton) rootView.findViewById(R.id.fabSend);
                    final EditText input = (EditText) rootView.findViewById(R.id.etMessageText);
                    fabSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("groups")
                                    .child(currentGroup)
                                    .child("messages")
                                    .push()
                                    .setValue(new Message(input.getText().toString(), name));

                            input.setText("");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }
}
