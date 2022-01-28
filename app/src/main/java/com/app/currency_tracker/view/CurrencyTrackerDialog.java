package com.app.currency_tracker.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.app.currency_tracker.R;

import java.util.Formatter;

public class CurrencyTrackerDialog extends DialogFragment {
    private CharSequence ticker;
    private Double value;
    private Integer nominal;

    public CurrencyTrackerDialog(CharSequence ticker, Double value, Integer nominal) {
        this.ticker = ticker;
        this.value = value;
        this.nominal = nominal;
    }

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewCustom = inflater.inflate(R.layout.dialog_conversation, null);

        EditText sum = (EditText) viewCustom.findViewById(R.id.rate_sum);
        TextView resultSum = (TextView) viewCustom.findViewById(R.id.result_of_sum);
        TextView tickerView = (TextView) viewCustom.findViewById(R.id.dialog_currency_ticker);

        builder.setView(viewCustom)
                .setNegativeButton("Отмена", null);

        sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged (Editable s) {
                String val = s.toString();
                if(val.equals("")) {
                    resultSum.setText("0");
                } else {
                    Formatter formatter = new Formatter();
                    Integer quantity = Integer.parseInt(val);
                    Double res = quantity * value / nominal;
                    formatter.format("%.2f", res);
                    resultSum.setText(formatter.toString());
                }
            }
        });

        tickerView.setText(ticker);

        return builder.create();
    }
}