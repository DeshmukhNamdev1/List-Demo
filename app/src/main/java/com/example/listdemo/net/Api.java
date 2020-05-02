package com.example.listdemo.net;

import com.example.listdemo.model.NetworkRecords;
import com.example.listdemo.utils.LiveDataCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Api {

    private static ApiInterface api;
    private static final String BASE_URL = "https://data.gov.sg/api/action/";

    public static ApiInterface getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
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
        String API_KEY = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f";

        @POST("datastore_search")
        Call<NetworkRecords> getFilterList(
                @Query("resource_id") String resource_id,
                @Query("limit") int limit,
                @Query("offset") long offset
        );
    }
}