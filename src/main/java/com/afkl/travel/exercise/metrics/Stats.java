package com.afkl.travel.exercise.metrics;

final class Stats {
    private long total;
    private double average;
    private long ok;
    private long fourXX;
    private long fiveXX;
    private double totalTime;
    private double maxTime;

    public long getTotal() {
        return total;
    }

    public Stats setTotal(long total) {
        this.total = total;
        return this;
    }

    public double getAverage() {
        return average;
    }

    public Stats setAverage(double average) {
        this.average = average;
        return this;
    }

    public long getOk() {
        return ok;
    }

    public Stats setOk(long ok) {
        this.ok = ok;
        return this;
    }

    public long getFourXX() {
        return fourXX;
    }

    public Stats setFourXX(long fourXX) {
        this.fourXX = fourXX;
        return this;
    }

    public long getFiveXX() {
        return fiveXX;
    }

    public Stats setFiveXX(long fiveXX) {
        this.fiveXX = fiveXX;
        return this;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public Stats setTotalTime(double totalTime) {
        this.totalTime = totalTime;
        return this;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public Stats setMaxTime(double maxTime) {
        this.maxTime = maxTime;
        return this;
    }
}
