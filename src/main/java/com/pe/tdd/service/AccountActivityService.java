package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.domain.CreditCardPaymentResponse;
import com.pe.tdd.domain.MobileCarrierRechargeBuyResponse;
import com.pe.tdd.domain.ServiceProviderPaymentResponse;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.domain.TefTransfer;

import java.util.List;

public interface AccountActivityService {
    List<AccountActivity> findActivitiesByAccount(Long accountNumber, String userName);

    AccountActivity addAccountActivity(ServiceProviderPaymentResponse serviceProviderPaymentResponse, Account account);

    AccountActivity addAccountActivity(SpeiTransfer speiTransfer, Account account);

    AccountActivity addAccountActivity(CreditCardPaymentResponse creditCardPaymentResponse, Account account);

    AccountActivity addAccountActivity(MobileCarrierRechargeBuyResponse mobileCarrierRechargeBuyResponse, Account account);

    AccountActivity addAccountActivity(TefTransfer tefTransfer, Account account);
}
