package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ChangeAccountNipResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getAccounts(String usuario);

    Account findAccount(Long accountNumber, String userName);

    Account updateBalance(BigDecimal amount, Account account);

    ChangeAccountNipResponse changeNip(String newNip, String actualNip, String accountNumber);
}
