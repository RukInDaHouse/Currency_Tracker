package com.app.currency_tracker.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.currency_tracker.data.DailyChangeOfCourses;
import com.app.currency_tracker.data.ChangeOfCourses;
import com.app.currency_tracker.api.ApiProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyTrackerViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ChangeOfCourses>> data = new MutableLiveData<>();
    private MutableLiveData<CharSequence> error = new MutableLiveData<>();

    public LiveData<ArrayList<ChangeOfCourses>> getData () { return data; }
    public  LiveData<CharSequence> getError () {return error; }
    public CurrencyTrackerViewModel() {
        error.setValue(null);
        data.setValue(new ArrayList<>());
        loadData();
    }


    public void loadData () {
        ApiProvider.getApiService()
            .getDailyExchangeRates()
            .enqueue(new Callback<DailyChangeOfCourses>() {
                @Override
                public void onResponse (Call<DailyChangeOfCourses> call, Response<DailyChangeOfCourses> response) {
                    error.setValue(null);
                    data.setValue(new ArrayList<ChangeOfCourses>(response.body().getValute().values()));
                }

                @Override
                public void onFailure (Call<DailyChangeOfCourses> call, Throwable t) {
                    error.setValue(t.toString());
                }
            });
    }
}
