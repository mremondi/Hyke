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
import com.google.firebase.database.FirebaseDatabase;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.Message;

public class MessagesFragment extends Fragment {

    private FirebaseListAdapter<Message> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_messages, null, false);




        ListView listOfMessages = (ListView)rootView.findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Message>(getActivity(), Message.class,
                R.layout.message, FirebaseDatabase.getInstance()
                                .getReference()
                                .child("groups")
                                .child("uid123")
                                .child("messages")) {
            @Override
            protected void populateView(View v, Message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.message);
                messageUser.setText(model.origin);

                // Format the date before showing it
                messageTime.setText(DateFormat.format("HH:mm",
                        model.dtime));
            }
        };

        listOfMessages.setAdapter(adapter);

        FloatingActionButton fabSend = (FloatingActionButton) rootView.findViewById(R.id.fabSend);
        final EditText input = (EditText)rootView.findViewById(R.id.etMessageText);
        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("groups")
                        .child("uid123")
                        .child("messages")
                        .push()
                        .setValue(new Message(input.getText().toString(),
                                "Mike")
                        );

                // Clear the input
                input.setText("");
            }
        });


        return rootView;
    }
}
