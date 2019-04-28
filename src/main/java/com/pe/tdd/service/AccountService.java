package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ChangeAccountNipResponse;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts(String usuario);

    Account findAccount(Long accountNumber, String userName);

    ChangeAccountNipResponse changeNip(String newNip, String actualNip, String accountNumber);
}
