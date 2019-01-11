package com.pe.tdd.repository;

import com.pe.tdd.domain.Account;

import java.util.Collections;
import java.util.List;

public class AccountRepository {
    public List<Account> findAccountsByUserName(String usuario) {
        return Collections.emptyList();
    }

    public Account findAccountByAccountNumberAndUserName(Long accountNumber, String userName) {
        return null;
    }
}
