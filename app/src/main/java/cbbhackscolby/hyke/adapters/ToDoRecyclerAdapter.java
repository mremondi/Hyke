package cbbhackscolby.hyke.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cbbhackscolby.hyke.R;
import cbbhackscolby.hyke.models.ToDo;

/**
 * Created by mremondi on 2/25/17.
 */

public class ToDoRecyclerAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<ToDo> todos;

    public ToDoRecyclerAdapter(List<ToDo> list, Context context){
        this.todos = list;
        this.context = context;
    }

    @Override
    public ToDoRecyclerAdapter.ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row, parent, false);
        return new ToDoViewHolder(rowView);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ToDoViewHolder)holder).bind(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    private static class ToDoViewHolder extends RecyclerView.ViewHolder  {
        private CheckBox cbDone;
        private TextView tvToDoName;
        private TextView tvToDoDescription;

        private ToDoViewHolder(View restaurantView) {
            super(restaurantView);
            cbDone = (CheckBox) restaurantView.findViewById(R.id.cbDone);
            tvToDoName = (TextView) restaurantView.findViewById(R.id.tvToDoName);
            tvToDoDescription = (TextView) restaurantView.findViewById(R.id.tvToDoDescription);
        }

        private void bind(final ToDo todo){

            cbDone.setChecked(todo.getDone());
            tvToDoName.setText(todo.getName());
            tvToDoDescription.setText(todo.getDescription());

        }
    }
}
