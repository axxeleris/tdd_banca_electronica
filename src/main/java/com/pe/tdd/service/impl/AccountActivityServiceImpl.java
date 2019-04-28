package com.pe.tdd.service.impl;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.domain.TefTransfer;
import com.pe.tdd.exception.InvalidAccountException;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;

import java.util.List;

public class AccountActivityServiceImpl implements AccountActivityService {

    private final AccountActivityRepository accountActivityRepository;

    private final AccountService accountService;

    public AccountActivityServiceImpl(AccountActivityRepository accountActivityRepository, AccountService accountService) {
        this.accountActivityRepository = accountActivityRepository;
        this.accountService = accountService;
    }

    @Override
    public List<AccountActivity> findActivitiesByAccount(Long accountNumber, String userName) {
        Account foundAccount = accountService.findAccount(accountNumber, userName);
        if (foundAccount == null) {
            throw new InvalidAccountException();
        }
        return accountActivityRepository.findActivitiesByAccount(accountNumber);
    }


    @Override
    public AccountActivity addAccountActivity(TefTransfer tefTransfer, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(tefTransfer.getAmount());
        accountActivity.setDescription("Transferencia SPEI");
        accountActivity.setAuthorizationCode(tefTransfer.getAuthorizationCode());
        accountActivity.setId(1L);

        return accountActivityRepository.save(accountActivity);
    }

}
