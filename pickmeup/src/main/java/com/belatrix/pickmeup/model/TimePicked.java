package com.belatrix.pickmeup.model;

import java.util.Calendar;

/**
 * Created by gzavaleta on 02/09/16.
 */
public class TimePicked {

    private int hourOfDay;

    private int minute;

    private int year;

    private int month;

    private int day;

    public long timePickedToMiliseconds() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, this.getHourOfDay());
        cal.set(Calendar.MINUTE, this.getMinute());
        cal.set(Calendar.YEAR, this.getYear());
        cal.set(Calendar.MONTH, this.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.getDay());
        return cal.getTimeInMillis();
    }

    public TimePicked(){

    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
