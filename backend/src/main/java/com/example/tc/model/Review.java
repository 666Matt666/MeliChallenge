package com.example.tc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    private double average;
    private int total;

    public double getAverage() {
        return average;
    }
    public void setAverage(double average) {
        this.average = average;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}