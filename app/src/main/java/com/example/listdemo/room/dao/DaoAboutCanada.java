package com.example.listdemo.room.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.listdemo.model.DataModel;
import com.example.listdemo.model.Record;

import java.util.List;


@Dao
public interface DaoAboutCanada {
    @Query("SELECT * FROM Record")
    DataSource.Factory<Integer, Record> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Record> brands);


    @Query("DELETE from DataModel")
    void deleteAll();

}

