package com.app.currency_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.currency_tracker.data.ChangeOfCourses;
import com.app.currency_tracker.view.CurrencyTrackerAdapter;
import com.app.currency_tracker.view.CurrencyTrackerViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CurrencyTrackerViewModel exchangeRateViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CurrencyTrackerAdapter exchangeRateAdapter = new CurrencyTrackerAdapter(this);
        recyclerView.setAdapter(exchangeRateAdapter);

        exchangeRateViewModel
            = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(CurrencyTrackerViewModel.class);

        LiveData<CharSequence> error = exchangeRateViewModel.getError();
        LiveData<ArrayList<ChangeOfCourses>> data = exchangeRateViewModel.getData();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.i("swiperefresh", "Обновление данных");
            exchangeRateViewModel.loadData();
            swipeRefreshLayout.setRefreshing(false);
        });

        data.observe(this, exchangeRates -> {
            exchangeRateAdapter.setData(exchangeRates);
            swipeRefreshLayout.setRefreshing(false);
        });

        error.observe(this, (err) -> {
            swipeRefreshLayout.setRefreshing(false);
            if (err != null)
                Toast.makeText(this, "Не могу получить данные. Пожалуйста, проверьте ваше интернет соединение.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu__button:
                finish();
                
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}