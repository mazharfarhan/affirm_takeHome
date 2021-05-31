package com.mazhar.affirm.models;

import java.util.List;

public class Loan {

    private double interestRate;
    private Long amount;
    private int id;
    private double defaultLikeHood;
    private String state ;

    public Loan(List<String> data) {

        this.interestRate = Double.parseDouble(data.get(0));
        this.amount = Long.valueOf(data.get(1));
        this.id = Integer.parseInt(data.get(2));
        this.defaultLikeHood = Double.parseDouble(data.get(3));
        this.state = data.get(4);

    }

    public double getDefaultLikeHood() {
        return defaultLikeHood;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public Long getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }


}
