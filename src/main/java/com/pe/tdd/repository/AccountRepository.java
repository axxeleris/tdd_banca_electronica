package com.pe.tdd.repository;

import com.pe.tdd.domain.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAccountsByUserName(String usuario);

    Account findAccountByAccountNumberAndUserName(Long accountNumber, String userName);

    Account findByAccountNumber(String accountNumber);

    Account save(Account account);
}
