package com.sarmila.mynotes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sarmila.todoapp.model.Task;
import com.sarmila.mynotes.pojo.TaskDao;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
