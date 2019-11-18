package com.example.listdemo.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.listdemo.room.repository.AboutCanadaRepository;

public class ListViewModel extends AndroidViewModel {


    private AboutCanadaRepository aboutCanadaRepository;

    public ListViewModel(Application application) {
        super(application);
        aboutCanadaRepository = new AboutCanadaRepository(application);
    }

}
