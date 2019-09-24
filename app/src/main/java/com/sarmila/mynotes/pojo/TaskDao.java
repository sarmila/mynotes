package com.sarmila.mynotes.pojo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sarmila.todoapp.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("Select * from Task")
    List<Task> getAll();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
