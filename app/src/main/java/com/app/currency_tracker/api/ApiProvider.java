package com.app.currency_tracker.api;

import retrofit2.Retrofit;

public class ApiProvider {
    private static ApiLink apiService;
    private ApiProvider() {}

    private static void initialize () {
        Retrofit retrofit = ApiAction.getRetrofit();
        apiService = retrofit.create(ApiLink.class);
    }

    public static ApiLink getApiService () {
        if (apiService == null) initialize();

        return apiService;
    }
}
