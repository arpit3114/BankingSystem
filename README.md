Banking Management System (Java + JDBC)

A console-based Banking Management System developed using Core Java and JDBC, integrated with MySQL for persistent data storage. 
The application enables users to register, log in, create bank accounts, and perform secure banking operations such as deposits, withdrawals, balance checks, and fund transfers.


Project Description:

This project simulates basic banking functionalities through a menu-driven console application. 
It demonstrates real-world use of JDBC, database transactions, and object-oriented programming concepts while ensuring data consistency and security using PIN-based validation.


Features:


👤 User Registration & Login

🏦 Bank Account Creation

💰 Deposit Money

💸 Withdraw Money

🔁 Transfer Money Between Accounts

📊 Check Account Balance


Technologies Used

Programming Language: Java

Database: MySQL

Connectivity: JDBC

Concepts: OOP, Exception Handling, SQL, Transactions


Database Setup:

CREATE DATABASE banking_system;
USE banking_system;

CREATE TABLE user (
    full_name VARCHAR(100),
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100)
);

CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    full_name VARCHAR(100),
    email VARCHAR(100),
    balance DOUBLE,
    security_pin VARCHAR(10)
);


Update database credentials in BankingApp.java:

String url = "jdbc:mysql://localhost:3306/banking_system";
String user = "root";
String password = "your_password";


Key Concepts Demonstrated:

JDBC CRUD operations

Database transactions (commit & rollback)

Prepared statements (SQL injection prevention)

Object-Oriented Programming

Console-based user interaction



🔐 PIN-based transaction security

🗄️ MySQL database integration
