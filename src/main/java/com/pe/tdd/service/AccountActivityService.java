package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.exception.InvalidAccountException;
import com.pe.tdd.repository.AccountActivityRepository;

import java.util.List;

public class AccountActivityService {

    private final AccountActivityRepository accountActivityRepository;

    private final AccountService accountService;

    public AccountActivityService(AccountActivityRepository accountActivityRepository, AccountService accountService) {
        this.accountActivityRepository = accountActivityRepository;
        this.accountService = accountService;
    }

    public List<AccountActivity> findActivitiesByAccount(Long accountNumber, String userName) {
        Account foundAccount = accountService.findAccount(accountNumber, userName);
        if(foundAccount == null) {
            throw new InvalidAccountException();
        }
        return accountActivityRepository.findActivitiesByAccount(accountNumber);
    }
}
