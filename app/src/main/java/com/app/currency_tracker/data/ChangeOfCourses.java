package com.app.currency_tracker.data;

import com.google.gson.annotations.SerializedName;

public class ChangeOfCourses {
    @SerializedName("CharCode")
    private String ticker;

    @SerializedName("Nominal")
    private Integer nominal;

    @SerializedName("Name")
    private String name;

    @SerializedName("Value")
    private Double value;

    @SerializedName("Previous")
    private Double previousValue;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getNominal () {
        return nominal;
    }

    public void setNominal (int nominal) {
        this.nominal = nominal;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getValue () {
        return value;
    }

    public void setValue (Double value) {
        this.value = value;
    }

    public Double getPreviousValue () {
        return previousValue;
    }

    public void setPreviousValue (double previousValue) {
        this.previousValue = previousValue;
    }
}
