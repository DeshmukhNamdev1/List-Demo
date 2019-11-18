package com.example.listdemo.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.listdemo.model.DataModel;

import java.util.List;


@Dao
public interface DaoAboutCanada {
    @Query("SELECT * FROM DataModel")
    LiveData<List<DataModel>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DataModel> brands);

    @Query("DELETE from DataModel")
    void deleteAll();

}

