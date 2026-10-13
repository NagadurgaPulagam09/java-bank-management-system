# Bank Management System (Java)

A console-based banking application built with core Java and OOP principles.

## Features
- Create Savings and Current accounts
- Deposit, withdraw and transfer money
- View balance and full transaction history
- Minimum balance rule for Savings (500) and overdraft limit for Current (5000)
- Custom exceptions for insufficient balance and account not found
- Input validation on every prompt

## Concepts Used
- OOP: abstraction, inheritance, polymorphism, encapsulation
- Custom checked exceptions
- Collections: ArrayList, LinkedHashMap
- java.time (LocalDateTime) for transaction timestamps
- Layered structure: model, service, exception, main

## Project Structure
```
src/com/bank
├── model       Account, SavingsAccount, CurrentAccount, Transaction
├── exception   InsufficientBalanceException, AccountNotFoundException
├── service     BankService
└── main        BankAppMain
```

## How to Run
1. Clone: `git clone https://github.com/NagadurgaPulagam09/java-bank-management-system.git`
2. Open in Eclipse (File → Import → Existing Projects into Workspace)
3. Run `com.bank.main.BankAppMain` as a Java Application

## Future Improvements
- Save accounts to a file or MySQL database
- Add PIN-based login
- Add JUnit tests
