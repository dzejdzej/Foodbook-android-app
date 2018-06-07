package com.robpercival.demoapp.rest.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.robpercival.demoapp.state.ApplicationState;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by User on 5/19/2018.
 */

public abstract class BaseServiceImpl {

    private static final String API_URL = ApplicationState.SERVER_IP + "/api/";

    protected Retrofit retrofit;

    protected BaseServiceImpl() {
        configRetrofit();
    }

    private void configRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder(); // .setDateFormat(DateFormat.);

// Register an adapter to manage the date types as long values
     /*   builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });*/


        Gson gson = builder.registerTypeAdapter(Date.class, new GsonDateAdapter()).create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();



    }


}
