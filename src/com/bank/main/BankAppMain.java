package com.bank.main;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankService;
import java.util.List;
import java.util.Scanner;

public class BankAppMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final BankService bank = new BankService();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (1-8): ");
            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> transfer();
                    case 5 -> checkBalance();
                    case 6 -> showHistory();
                    case 7 -> listAccounts();
                    case 8 -> System.out.println("Thank you for banking with us. Goodbye!");
                    default -> System.out.println("Invalid option.");
                }
            } catch (AccountNotFoundException | InsufficientBalanceException
                     | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 8);
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n=== Bank Management System ===");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Check Balance");
        System.out.println("6. Transaction History");
        System.out.println("7. List All Accounts");
        System.out.println("8. Exit");
    }

    private static void createAccount() {
        String name = readText("Holder name: ");
        int type = readInt("Account type (1 = Savings, 2 = Current): ");
        if (type != 1 && type != 2) {
            System.out.println("Invalid account type.");
            return;
        }
        double opening = readDouble("Opening balance: ");
        Account acc = bank.createAccount(type == 1, name, opening);
        System.out.println("Account created. Your account number is " + acc.getAccountNumber());
    }

    private static void deposit() throws AccountNotFoundException {
        String number = readText("Account number: ");
        double amount = readDouble("Amount to deposit: ");
        bank.deposit(number, amount);
        System.out.println("Deposit successful.");
    }

    private static void withdraw()
            throws AccountNotFoundException, InsufficientBalanceException {
        String number = readText("Account number: ");
        double amount = readDouble("Amount to withdraw: ");
        bank.withdraw(number, amount);
        System.out.println("Withdrawal successful.");
    }

    private static void transfer()
            throws AccountNotFoundException, InsufficientBalanceException {
        String from = readText("From account number: ");
        String to = readText("To account number: ");
        double amount = readDouble("Amount to transfer: ");
        bank.transfer(from, to, amount);
        System.out.println("Transfer successful.");
    }

    private static void checkBalance() throws AccountNotFoundException {
        Account acc = bank.getAccount(readText("Account number: "));
        System.out.printf("Balance of %s: %.2f%n", acc.getAccountNumber(), acc.getBalance());
    }

    private static void showHistory() throws AccountNotFoundException {
        Account acc = bank.getAccount(readText("Account number: "));
        List<Transaction> list = acc.getTransactions();
        System.out.println("--- Transaction history for " + acc.getAccountNumber() + " ---");
        if (list.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction t : list) {
            System.out.println(t);
        }
    }

    private static void listAccounts() {
        List<Account> all = bank.getAllAccounts();
        if (all.isEmpty()) {
            System.out.println("No accounts yet.");
            return;
        }
        all.forEach(System.out::println);
    }

    // ---------- input helpers (no crashes on bad input) ----------

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
