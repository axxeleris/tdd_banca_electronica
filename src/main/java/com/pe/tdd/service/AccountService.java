package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts(String usuario) {
        return accountRepository.findAccountsByUserName(usuario);
    }

    public Account findAccount(Long accountNumber, String userName) {
        return accountRepository.findAccountByAccountNumberAndUserName(accountNumber, userName);
    }

    public Account updateBalance(BigDecimal amount, Account account) {
        if (account == null) {
            throw new IllegalArgumentException("La cuenta es requerida");
        }
        if (amount == null) {
            throw new IllegalArgumentException("El monto es requerido");
        }

        account.setBalance(
                account.getBalance().add(amount)
        );

        return accountRepository.save(account);
    }
}
