package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {
    private final String accountNumber;
    private final String holderName;
    private double balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, String holderName, double openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = openingBalance;
        if (openingBalance > 0) {
            transactions.add(new Transaction("OPENING", openingBalance, balance));
        }
    }

    public void deposit(double amount) {
        deposit(amount, "DEPOSIT");
    }

    public void deposit(double amount, String type) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        balance += amount;
        transactions.add(new Transaction(type, amount, balance));
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        withdraw(amount, "WITHDRAW");
    }

    public void withdraw(double amount, String type) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance - amount < getMinimumBalance()) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Available to withdraw: "
                            + String.format("%.2f", balance - getMinimumBalance()));
        }
        balance -= amount;
        transactions.add(new Transaction(type, amount, balance));
    }

    public abstract double getMinimumBalance();

    public abstract String getAccountType();

    public String getAccountNumber() { return accountNumber; }

    public String getHolderName() { return holderName; }

    public double getBalance() { return balance; }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toString() {
        return String.format("%-9s %-10s %-20s Balance: %.2f",
                accountNumber, getAccountType(), holderName, balance);
    }
}