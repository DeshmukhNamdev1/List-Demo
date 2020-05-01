package com.example.listdemo.model;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Record {

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("volume_of_mobile_data")
    @Expose
    private String volumeOfMobileData;
    @SerializedName("quarter")
    @Expose
    private String quarter;


    public String getVolumeOfMobileData() {
        return volumeOfMobileData;
    }

    public void setVolumeOfMobileData(String volumeOfMobileData) {
        this.volumeOfMobileData = volumeOfMobileData;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "volumeOfMobileData='" + volumeOfMobileData + '\'' +
                ", quarter='" + quarter + '\'' +
                ", id=" + id +
                '}';
    }

    public static DiffUtil.ItemCallback<Record> DIFF_CALLBACK = new DiffUtil.ItemCallback<Record>() {
        @Override
        public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Record article = (Record) obj;
        return article.id == this.id;
    }
}
