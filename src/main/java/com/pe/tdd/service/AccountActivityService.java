package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.domain.TefTransfer;

import java.util.List;

public interface AccountActivityService {
    List<AccountActivity> findActivitiesByAccount(Long accountNumber, String userName);

    AccountActivity addAccountActivity(TefTransfer tefTransfer, Account account);
}
