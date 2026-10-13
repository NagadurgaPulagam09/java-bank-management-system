package com.bank.service;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.SavingsAccount;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private int nextNumber = 1001;

    public Account createAccount(boolean savings, String name, double openingBalance) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (savings && openingBalance < SavingsAccount.MIN_BALANCE) {
            throw new IllegalArgumentException(
                    "Savings account needs an opening balance of at least "
                            + SavingsAccount.MIN_BALANCE);
        }
        String number = "ACC" + nextNumber++;
        Account account = savings
                ? new SavingsAccount(number, name.trim(), openingBalance)
                : new CurrentAccount(number, name.trim(), openingBalance);
        accounts.put(number, account);
        return account;
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber.trim().toUpperCase());
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        getAccount(accountNumber).deposit(amount);
    }

    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {
        getAccount(accountNumber).withdraw(amount);
    }

    public void transfer(String fromNumber, String toNumber, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {
        Account from = getAccount(fromNumber);
        Account to = getAccount(toNumber);
        if (from == to) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        from.withdraw(amount, "TRANSFER OUT");
        to.deposit(amount, "TRANSFER IN");
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
}