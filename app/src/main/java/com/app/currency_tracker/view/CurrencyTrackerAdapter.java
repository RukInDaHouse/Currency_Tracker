package com.app.currency_tracker.view;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.currency_tracker.R;
import com.app.currency_tracker.data.ChangeOfCourses;

import java.util.ArrayList;
import java.util.Formatter;

public class CurrencyTrackerAdapter extends RecyclerView.Adapter<CurrencyTrackerAdapter.ViewHolder> {
    private ArrayList<ChangeOfCourses> exchangeRates;
    private FragmentActivity context;
    private LayoutInflater inflater;

    public CurrencyTrackerAdapter(FragmentActivity context) {
        this.exchangeRates = new ArrayList<>();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData (ArrayList<ChangeOfCourses> rates) {
        this.exchangeRates = rates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_exchange_rate_list, parent, false);

        return new ViewHolder(view);
    }

    public String clampString(String str) {
        String showValueName;

        if (str.length() > 24) {
            showValueName = str.substring(0, 24) + "...";
        } else showValueName = str;

        return showValueName;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder (@NonNull CurrencyTrackerAdapter.ViewHolder holder, int position) {
        Formatter formatter = new Formatter();
        Formatter formatterPercent = new Formatter();

        ChangeOfCourses rate = exchangeRates.get(position);
        String valueNameString = clampString(rate.getName());
        formatter.format("%.2f ₽ / %d %s", rate.getValue(), rate.getNominal(), rate.getTicker());

        holder.currencyName.setText(valueNameString);
        holder.currencyRate.setText(formatter.toString());

        Double percent = rate.getValue() * 100 / rate.getPreviousValue() - 100;
        Double per =  Math.abs(Math.round(percent * 100.0) / 100.0);
        formatterPercent.format("%.2f%%", per);

        holder.currencyPercent.setText(formatterPercent.toString());

        Resources res = context.getResources();
        if (percent < 0) {
            holder.currencyPercentArrow.setImageResource(R.drawable.decrease_arrow);
            holder.currencyPercent.setTextColor(res.getColor(R.color.decrease));
        } else {
            holder.currencyPercentArrow.setImageResource(R.drawable.increase_arrow);
            holder.currencyPercent.setTextColor(res.getColor(R.color.increase));
        }

        int id = context.getResources().
                getIdentifier("ic_" + rate.getTicker().toLowerCase(),
                        "drawable",
                        context.getPackageName());

        if (id == 0)
            holder.currencyFlag.setImageResource(R.drawable.ic_unknown);
        else holder.currencyFlag.setImageResource(id);

        holder.root.setOnClickListener(v -> {
            CurrencyTrackerDialog dialogCustom
                    = new CurrencyTrackerDialog(rate.getTicker(), rate.getValue(), rate.getNominal());
            dialogCustom.show(context.getSupportFragmentManager(), "DialogCustom");
        });
    }

    @Override
    public int getItemCount () {
        return exchangeRates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView currencyName;
        final TextView currencyRate;
        final TextView currencyPercent;
        final ImageView currencyFlag;
        final ImageView currencyPercentArrow;
        final View root;

        public ViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.rate_root);
            currencyName = (TextView) view.findViewById(R.id.currency_name);
            currencyRate = (TextView) view.findViewById(R.id.currency_rate);
            currencyPercent = (TextView) view.findViewById(R.id.currency_percent);
            currencyFlag = (ImageView) view.findViewById(R.id.currency_flag);
            currencyPercentArrow = (ImageView) view.findViewById(R.id.currency_percent_arrow);
        }
    }
}
