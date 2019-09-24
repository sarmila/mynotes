package com.sarmila.mynotes.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sarmila.mynotes.DatabaseClient;
import com.sarmila.todoapp.R;
import com.sarmila.mynotes.adapter.TaskAdapter;
import com.sarmila.todoapp.model.Task;

import java.util.List;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_task;
    private FloatingActionButton fab_addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initViews();
        getTasks();
    }

    private void getTasks() {
        class GetTasks extends AsyncTask <Void, Void, List<Task>>{

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                setAdapter(tasks);
            }
        }
        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }

    private void setAdapter(List<Task> taskList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_task.setLayoutManager(linearLayoutManager);

        TaskAdapter taskAdapter = new TaskAdapter(this , taskList);
        rv_task.setAdapter(taskAdapter);

    }

    private void initViews() {
        rv_task = findViewById(R.id.rv_task);
        fab_addTask = findViewById(R.id.fab_addTask);
        fab_addTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_addTask:
                Intent intent = new Intent(this, AddTaskActivity.class);
                startActivity(intent);
        }
    }
}
