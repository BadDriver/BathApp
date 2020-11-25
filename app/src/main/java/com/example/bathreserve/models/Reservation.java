package com.example.bathreserve.models;

import java.io.Serializable;
import java.time.DayOfWeek;

public class Reservation implements Serializable {
    private String dayOfWeek;
    private int hour;
    private int minute;
    private String userId;
    private String userName;

    public Reservation(String dayOfWeek, int hour, int minute, String userId, String userName) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
        this.minute = minute;
        this.userId = userId;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
