package com.altoque.delivery.api;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {

    public static final int DEFAULT_RETRIES = 10;

    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        call.enqueue(new RetryableCallback<T>(call, retryCount) {

            @Override
            public void onFinalResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        enqueueWithRetry(call, DEFAULT_RETRIES, callback);
    }

    public static boolean isCallSuccess(@NonNull Response response) {
        int code = response.code();
        return (code >= 200 && code < 400);
    }

}
