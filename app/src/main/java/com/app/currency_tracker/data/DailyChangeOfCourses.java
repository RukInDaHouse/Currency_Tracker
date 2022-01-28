package com.app.currency_tracker.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;

public class DailyChangeOfCourses {
    @SerializedName("Date")
    private Date date;

    @SerializedName("Timestamp")
    private Date timestamp;

    @SerializedName("Valute")
    private Map<String, ChangeOfCourses> valute;

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public Date getTimestamp () {
        return timestamp;
    }

    public void setTimestamp (Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, ChangeOfCourses> getValute () {
        return valute;
    }

    public void setValute (Map<String, ChangeOfCourses> valute) {
        this.valute = valute;
    }
}
