package com.sarmila.mynotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarmila.todoapp.R;
import com.sarmila.mynotes.activity.UpdateTaskActivity;
import com.sarmila.todoapp.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context context;
    private List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder viewHolder, int i) {
        viewHolder.tv_taskName.setText(taskList.get(i).getTaskName());
        viewHolder.tv_taskDesc.setText(taskList.get(i).getTaskDesc());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_taskName, tv_taskDesc;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_taskDesc = itemView.findViewById(R.id.tv_taskDesc);
            tv_taskName = itemView.findViewById(R.id.tv_taskName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(context, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            context.startActivity(intent);
        }
    }
}
