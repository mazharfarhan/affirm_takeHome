package com.mazhar.affirm.models;

import java.util.List;

public class Bank {

    private int bankId;
    private String bankName;

    public Bank(List<String> data) {
        this.bankId = Integer.parseInt(data.get(0));
        this.bankName = data.get(1);
    }


    public int getBankId() {
        return bankId;
    }

    public String getBankName() {
        return bankName;
    }
}
