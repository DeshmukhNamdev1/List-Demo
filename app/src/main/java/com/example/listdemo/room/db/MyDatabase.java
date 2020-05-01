package com.example.listdemo.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.listdemo.model.DataModel;
import com.example.listdemo.model.Record;
import com.example.listdemo.room.dao.DaoAboutCanada;
@Database(entities = {DataModel.class,Record.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static String DB_NAME = "lost_demo";
    private static volatile MyDatabase INSTANCE;

    public static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DaoAboutCanada daoAboutCanada();


}
