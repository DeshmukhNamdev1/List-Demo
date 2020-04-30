package com.example.listdemo.room.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.listdemo.dto.ApiResponse;
import com.example.listdemo.dto.Resource;
import com.example.listdemo.model.DataModel;
import com.example.listdemo.net.Api;
import com.example.listdemo.room.db.MyDatabase;
import com.example.listdemo.utils.NetworkBoundResource;

import java.util.List;


public class AboutCanadaRepository {

    private MyDatabase myDatabase;

    public AboutCanadaRepository(Context context) {
        myDatabase = MyDatabase.getDatabase(context);
    }

    public void insertAll(final List<DataModel> dataModels) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                myDatabase.daoAboutCanada().insertAll(dataModels);
                return null;
            }
        }.execute();
    }

    public LiveData<Resource<List<DataModel>>> getAboutCanadas() {
        LiveData<Resource<List<DataModel>>> liveData = new NetworkBoundResource<List<DataModel>, List<DataModel>>() {
            @Override
            protected void saveCallResult(@NonNull List<DataModel> items) {
                insertAll(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<DataModel> data) {
                return true;//let's always refresh to be up to date. data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<DataModel>> loadFromDb() {
                return myDatabase.daoAboutCanada().getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<DataModel>>> createCall() {
                return Api.getApi().getAboutCanadas();
            }
        }.getAsLiveData();

        return liveData;
    }

}