package com.sarmila.mynotes.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sarmila.mynotes.DatabaseClient;
import com.sarmila.todoapp.R;
import com.sarmila.todoapp.model.Task;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_taskName, et_taskDesc;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        initViews();
    }

    private void saveData() {
        if (valid()) {
            SaveTask saveTask = new SaveTask();
            saveTask.execute();
        }

    }

    private boolean valid() {
        if (et_taskName.getText().toString().isEmpty()) {
            et_taskName.setError("Task Name Required");
            et_taskName.requestFocus();
            return false;
        }
        if (et_taskDesc.getText().toString().isEmpty()) {
            et_taskDesc.setError("Task Description Required");
            et_taskDesc.requestFocus();
            return false;
        }
        return true;
    }

    private void initViews() {
        et_taskName = findViewById(R.id.et_taskName);
        et_taskDesc = findViewById(R.id.et_taskDesc);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveData();
        }
    }

    class SaveTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Task task = new Task();
            task.setTaskName(et_taskName.getText().toString());
            task.setTaskDesc(et_taskDesc.getText().toString());

            DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().insert(task);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            finish();
            startActivity(new Intent(getApplicationContext(), TaskActivity.class));
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        }
    }
}
