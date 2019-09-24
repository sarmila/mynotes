package com.sarmila.mynotes;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;

    private DatabaseClient(Context mContext){
        this.context = mContext;

        appDatabase = Room.databaseBuilder(context,AppDatabase.class, "TodoDB").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if(mInstance == null){
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }
    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}
