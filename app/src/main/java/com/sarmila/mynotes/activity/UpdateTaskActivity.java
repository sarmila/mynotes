package com.sarmila.mynotes.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sarmila.mynotes.DatabaseClient;
import com.sarmila.todoapp.R;
import com.sarmila.todoapp.model.Task;

public class UpdateTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_update_taskName, et_update_taskDesc;
    private Button btn_update_task, btn_delete_task;
    private Task task;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_task);
        initViews();
        getTask();
        loadTask();
    }

    private void getTask() {
        task = (Task) getIntent().getSerializableExtra("task");
    }

    private void initViews() {
        et_update_taskName = findViewById(R.id.et_update_taskName);
        et_update_taskDesc = findViewById(R.id.et_update_taskDesc);
        btn_delete_task = findViewById(R.id.btn_delete_task);
        btn_update_task = findViewById(R.id.btn_update_task);

        btn_update_task.setOnClickListener(this);
        btn_delete_task.setOnClickListener(this);
    }

    private void loadTask() {
        et_update_taskName.setText(task.getTaskName());
        et_update_taskDesc.setText(task.getTaskDesc());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_task:
                updateClick();
                break;
            case R.id.btn_delete_task:
                deleteClick();
                break;
        }
    }

    private void deleteClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
        builder.setTitle("Are you sure?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTask(task);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }

    private void deleteTask(final Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, TaskActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

    private void updateClick() {
        updateData();
    }

    private void updateData() {
        if (valid()) {
            UpdateTask updateTask = new UpdateTask();
            updateTask.execute();
        }
    }

    private boolean valid() {
        if (et_update_taskName.getText().toString().isEmpty()) {
            et_update_taskName.setError("Task Name Required");
            et_update_taskName.requestFocus();
            return false;
        }
        if (et_update_taskDesc.getText().toString().isEmpty()) {
            et_update_taskDesc.setError("Task Description Required");
            et_update_taskDesc.requestFocus();
            return false;
        }
        return true;
    }


    class UpdateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Task task = new Task();
            task.setTaskName(et_update_taskName.getText().toString());
            task.setTaskDesc(et_update_taskDesc.getText().toString());

            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().update(task);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
           // finish();
            startActivity(new Intent(getApplicationContext(), TaskActivity.class));
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
        }
    }
}
