package com.example.triviavirsion2;

public class Streak {
    private int days;
    private long timestamp;

    public Streak() {
    }

    public Streak(int days) {
        this.days = days;
        timestamp = System.currentTimeMillis();
    }

    public Streak(int days, long timestamp) {
        this.days = days;
        this.timestamp = timestamp;
    }

    public int getDays() {
        return days;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
