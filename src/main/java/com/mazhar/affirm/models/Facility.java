package com.mazhar.affirm.models;


import java.util.List;

public class Facility {




    private Long amount;
    private double interestRate;
    private int id;
    private int bankId;

    public Facility(List<String> data) {
        this.amount = Long.valueOf(data.get(0));
        this.interestRate = Double.parseDouble(data.get(1));
        this.id = Integer.parseInt(data.get(2));
        this.bankId = Integer.parseInt(data.get(3));
    }


    public int getId() {
        return id;
    }

    public int getBankId() {
        return bankId;
    }

    public Long getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
