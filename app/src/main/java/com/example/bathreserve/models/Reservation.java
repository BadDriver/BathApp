package com.example.bathreserve.models;

import java.io.Serializable;
import java.time.DayOfWeek;

public class Reservation implements Serializable {
    private String dayOfWeek;
    private int hour;
    private int minute;
    private String userId;

    public Reservation(String dayOfWeek, int hour, int minute, String userId) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
        this.userId = userId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "dayOfWeek=" + dayOfWeek +
                ", hour=" + hour +
                ", minute=" + minute +
                ", userId='" + userId + '\'' +
                '}';
    }
}
