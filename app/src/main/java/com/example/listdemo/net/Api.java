package com.example.listdemo.net;

import androidx.lifecycle.LiveData;

import com.example.listdemo.dto.ApiResponse;
import com.example.listdemo.model.DataModel;
import com.example.listdemo.utils.LiveDataCallAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Api {

    private static ApiInterface api;
    private static final String BASE_URL = "http://jsonplaceholder.typicode.com";

    public static ApiInterface getApi() {
        if (api == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .build();

            api = retrofit.create(ApiInterface.class);
        }
        return api;
    }

    public interface ApiInterface {
        @GET("/photos")
        LiveData<ApiResponse<List<DataModel>>> getAboutCanadas();

    }
}