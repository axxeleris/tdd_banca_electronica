package com.pe.tdd.repository;

import com.pe.tdd.domain.AccountActivity;

import java.util.List;

public interface AccountActivityRepository {
    List<AccountActivity> findActivitiesByAccount(Long accountNumber);

    AccountActivity save(AccountActivity accountActivity);
}
