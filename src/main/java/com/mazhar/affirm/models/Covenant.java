package com.mazhar.affirm.models;

import java.util.List;

public class Covenant {

    private int facilityId = 0;
    private double maxDefaultLikeliHood = Integer.MAX_VALUE;
    private int bankId;
    private String state;

    public Covenant(List<String> data) {
        this.facilityId = Integer.parseInt(data.get(0));
        this.maxDefaultLikeliHood = Double.parseDouble(data.get(1));
        this.bankId = Integer.parseInt(data.get(2));
        this.state = data.get(3);
    }

    public int getFacilityId() {
        return facilityId;
    }

    public double getMaxDefaultLikeliHood() {
        return maxDefaultLikeliHood;
    }

    public int getBankId() {
        return bankId;
    }

    public String getState() {
        return state;
    }

}
