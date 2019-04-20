package com.pe.tdd.service;

import com.pe.tdd.domain.AccountActivity;

import java.util.List;

public interface AccountActivityService {
    List<AccountActivity> findActivitiesByAccount(Long accountNumber, String userName);
}
