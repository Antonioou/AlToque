package com.altoque.delivery.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://apialtoquedelivery.000webhostapp.com/";
    //public static final String BASE_URL="http://61c38c1db234.ngrok.io/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    //.addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
