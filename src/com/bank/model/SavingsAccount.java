package com.bank.model;

public class SavingsAccount extends Account {
    public static final double MIN_BALANCE = 500;

    public SavingsAccount(String accountNumber, String holderName, double openingBalance) {
        super(accountNumber, holderName, openingBalance);
    }

    @Override
    public double getMinimumBalance() {
        return MIN_BALANCE;
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
}