package com.example.listdemo.room.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.paging.DataSource;

import com.example.listdemo.model.Record;
import com.example.listdemo.room.db.MyDatabase;

import java.util.List;


public class AboutCanadaRepository {

    private MyDatabase myDatabase;

    public AboutCanadaRepository(Context context) {
        myDatabase = MyDatabase.getDatabase(context);
    }

    public void insertAll(final List<Record> dataModels) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.daoAboutCanada().insertAll(dataModels);
                return null;
            }
        }.execute();
    }


    public DataSource.Factory<Integer, Record> getAll() {
        return myDatabase.daoAboutCanada().getAll();
    }
}