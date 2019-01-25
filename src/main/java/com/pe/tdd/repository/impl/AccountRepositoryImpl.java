package com.pe.tdd.repository.impl;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.AccountRepository;

import java.util.Collections;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public List<Account> findAccountsByUserName(String usuario) {
        return Collections.emptyList();
    }

    @Override
    public Account findAccountByAccountNumberAndUserName(Long accountNumber, String userName) {
        return null;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public Account save(Account account) {
        return account;
    }
}
