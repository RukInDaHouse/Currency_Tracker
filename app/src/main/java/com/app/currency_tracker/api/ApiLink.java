package com.app.currency_tracker.api;

import com.app.currency_tracker.data.DailyChangeOfCourses;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiLink {
    @GET("/daily_json.js")
    Call<DailyChangeOfCourses> getDailyExchangeRates();
}
