package com.pe.tdd.repository.impl;

import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.repository.AccountActivityRepository;

import java.util.Collections;
import java.util.List;

public class AccountActivityRepositoryImpl implements AccountActivityRepository {

    @Override
    public List<AccountActivity> findActivitiesByAccount(Long accountNumber) {
        return Collections.emptyList();
    }

    @Override
    public AccountActivity save(AccountActivity accountActivity) {
        return accountActivity;
    }

}
