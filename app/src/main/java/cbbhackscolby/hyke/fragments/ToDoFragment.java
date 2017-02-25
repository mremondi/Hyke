package cbbhackscolby.hyke.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.ToDo;


public class ToDoFragment extends Fragment {

    List<ToDo> toDos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_to_do, null, false);

        final RecyclerView toDoRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_favorites);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        toDoRecyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabAddTodo);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDos.add(new ToDo("Hello", "Bye"));
                toDoRecyclerView.notify();
            }
        });

        return rootView;
    }
}