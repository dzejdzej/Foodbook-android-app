package com.robpercival.demoapp.rest.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by User on 5/19/2018.
 */

public abstract class BaseServiceImpl {

    private static final String API_URL = "http://192.168.0.12:8080/api/";

    protected Retrofit retrofit;

    protected BaseServiceImpl() {
        configRetrofit();
    }

    private void configRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();



    }


}
