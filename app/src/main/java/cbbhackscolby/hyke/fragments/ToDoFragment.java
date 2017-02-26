package cbbhackscolby.hyke.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.ToDoPopup;
import cbbhackscolby.hyke.adapters.ToDoRecyclerAdapter;
import cbbhackscolby.hyke.models.ToDo;


public class ToDoFragment extends Fragment implements ToDoPopup.ToDoPopupListener{

    List<ToDo> toDos = new ArrayList<>();

    ToDoRecyclerAdapter todoRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_to_do, null, false);

        todoRecyclerAdapter = new ToDoRecyclerAdapter(toDos, getContext());
        final RecyclerView toDoRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_favorites);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        final Button cleartodo = (Button) rootView.findViewById(R.id.cleartodo);
        toDoRecyclerView.setLayoutManager(linearLayoutManager);
        toDoRecyclerView.setAdapter(todoRecyclerAdapter);

        final ToDoPopup.ToDoPopupListener listener = this;
        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabAddTodo);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoPopup popup = new ToDoPopup();
                popup.mListener = listener;
                popup.show(getFragmentManager(), "ToDoPopup");
            }
        });
        cleartodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HERE","TESTING");

            }
        });
        return rootView;
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog dialogView = dialog.getDialog();
        EditText etTodoName = (EditText) dialogView.findViewById(R.id.etTodoName);


        EditText etTodoDescription = (EditText) dialogView.findViewById(R.id.etTodoDescription);

        ToDo toDo = new ToDo(etTodoName.getText().toString(), etTodoDescription.getText().toString());

        if(toDo.getName().equals("")){

        }
        else {
            toDos.add(toDo);
            todoRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
}