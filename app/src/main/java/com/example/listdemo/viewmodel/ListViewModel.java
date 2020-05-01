package com.example.listdemo.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.listdemo.fragments.util.NetworkState;
import com.example.listdemo.model.Record;
import com.example.listdemo.room.repository.AboutCanadaRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListViewModel extends AndroidViewModel {


    private final AppExecutors executor;
    private AboutCanadaRepository aboutCanadaRepository;
    private Application application;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Record>> articleLiveData;
    private UserBoundaryCallback userBoundaryCallback;

    public ListViewModel(Application application) {
        super(application);
        aboutCanadaRepository = new AboutCanadaRepository(application);
        this.application = application;
        executor = new AppExecutors();
        init();
    }

    private void init() {
//        executor = Executors.newFixedThreadPool(5);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();
        userBoundaryCallback = new UserBoundaryCallback(aboutCanadaRepository, executor);
        networkState = userBoundaryCallback.getNetworkState();
        articleLiveData = (new LivePagedListBuilder(aboutCanadaRepository.getAll(), pagedListConfig)).setBoundaryCallback(userBoundaryCallback)
//                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Record>> getArticleLiveData() {
        return articleLiveData;
    }

}
