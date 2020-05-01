package com.example.listdemo.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.listdemo.fragments.util.NetworkState;
import com.example.listdemo.model.NetworkRecords;
import com.example.listdemo.model.Record;
import com.example.listdemo.net.Api;
import com.example.listdemo.room.repository.AboutCanadaRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserBoundaryCallback extends PagedList.BoundaryCallback<Record> {
    public static final String TAG = "ItemKeyedUserDataSource";
    private AboutCanadaRepository aboutCanadaRepository;
    AppExecutors executors;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public UserBoundaryCallback(AboutCanadaRepository aboutCanadaRepository, AppExecutors executors) {
        super();
        this.aboutCanadaRepository = aboutCanadaRepository;
        this.executors = executors;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void onZeroItemsLoaded() {
        //super.onZeroItemsLoaded();
        fetchFromNetwork(null);
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull Record itemAtFront) {
        //super.onItemAtFrontLoaded(itemAtFront);
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Record itemAtEnd) {
         super.onItemAtEndLoaded(itemAtEnd);
        fetchFromNetwork(itemAtEnd);
    }

    public void fetchFromNetwork(Record user) {
        if (user == null) {
            user = new Record();
            user.setId(1);
        }
        networkState.postValue(NetworkState.LOADING);
        Api.getApi().getFilterList(Api.ApiInterface.API_KEY, 10, user.getId()).enqueue(new Callback<NetworkRecords>() {
            @Override
            public void onResponse(Call<NetworkRecords> call, Response<NetworkRecords> response) {
                executors.diskIO().execute(() -> {
                    if (response.body() != null)
                        aboutCanadaRepository.insertAll(response.body().getResult().getRecords());
                    networkState.postValue(NetworkState.LOADED);
                });
            }

            @Override
            public void onFailure(Call<NetworkRecords> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                Log.d(TAG, errorMessage);
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });

    }

}