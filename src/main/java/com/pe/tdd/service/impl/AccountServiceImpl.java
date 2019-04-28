package com.pe.tdd.service.impl;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ChangeAccountNipResponse;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAccounts(String usuario) {
        return accountRepository.findAccountsByUserName(usuario);
    }

    @Override
    public Account findAccount(Long accountNumber, String userName) {
        return accountRepository.findAccountByAccountNumberAndUserName(accountNumber, userName);
    }

    @Override
    public ChangeAccountNipResponse changeNip(String newNip, String actualNip, String accountNumber) {
        if (StringUtils.isEmpty(newNip)) {
            throw new IllegalArgumentException("El nuevo nip es requerido");
        }
        if (StringUtils.isEmpty(actualNip)) {
            throw new IllegalArgumentException("El nip actual es requerido");
        }
        if (StringUtils.length(newNip) != 4) {
            throw new IllegalArgumentException("El nip debe ser de 4 digitos");
        }
        if (StringUtils.isEmpty(accountNumber)) {
            throw new IllegalArgumentException("El numero de cuenta es requerido");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException("La cuenta es invalida");
        }

        if (isValidNip(actualNip, account)) {
            account.setNip(newNip);
        } else {
            throw new IllegalArgumentException("El nip actual es invalido");
        }

        account.setNip(newNip);

        accountRepository.save(account);

        return new ChangeAccountNipResponse("00000");
    }

    private boolean isValidNip(String nip, Account account) {
        return account.getNip().equals(nip);
    }
}
