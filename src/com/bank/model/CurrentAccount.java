package com.bank.model;

public class CurrentAccount extends Account {
    public static final double OVERDRAFT_LIMIT = 5000;

    public CurrentAccount(String accountNumber, String holderName, double openingBalance) {
        super(accountNumber, holderName, openingBalance);
    }

    @Override
    public double getMinimumBalance() {
        return -OVERDRAFT_LIMIT;
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }
}
