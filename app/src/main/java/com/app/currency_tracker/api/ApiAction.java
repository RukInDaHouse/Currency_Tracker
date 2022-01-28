package com.app.currency_tracker.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.currency_tracker.api.ApiData.BASE_URL;
import static com.app.currency_tracker.api.ApiData.READ_TIMEOUT;

public class ApiAction {
    private static Retrofit mRetrofit;
    private ApiAction() {}

    private static OkHttpClient getOkHttp () {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);

        return okHttpClient.build();
    }

    private static void initialize () {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttp())
                .build();
    }

    public static Retrofit getRetrofit () {
        if(mRetrofit == null) initialize();

        return mRetrofit;
    }
}
