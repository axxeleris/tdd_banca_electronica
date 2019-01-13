package com.pe.tdd.domain;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private String description;
    private BigDecimal balance;

    public Account(String accountNumber, String description, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.description = description;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
