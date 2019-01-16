package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.domain.CreditCardPaymentResponse;
import com.pe.tdd.domain.MobileCarrierRechargeBuyResponse;
import com.pe.tdd.domain.ServiceProviderPaymentResponse;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.domain.TefTransfer;
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
        if (foundAccount == null) {
            throw new InvalidAccountException();
        }
        return accountActivityRepository.findActivitiesByAccount(accountNumber);
    }

    public AccountActivity addAccountActivity(ServiceProviderPaymentResponse serviceProviderPaymentResponse, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(serviceProviderPaymentResponse.getAmount());
        accountActivity.setDescription(serviceProviderPaymentResponse.getDescription());
        accountActivity.setAuthorizationCode(serviceProviderPaymentResponse.getAuthorizationCode());
        accountActivity.setId(1L);

        accountService.updateBalance(
                accountActivity.getAmount().negate(),
                account
        );

        return accountActivityRepository.save(accountActivity);
    }

    public AccountActivity addAccountActivity(SpeiTransfer speiTransfer, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(speiTransfer.getAmount());
        accountActivity.setDescription("Transferencia SPEI");
        accountActivity.setAuthorizationCode(speiTransfer.getAuthorizationCode());
        accountActivity.setId(1L);

        accountService.updateBalance(
                accountActivity.getAmount().negate(),
                account
        );

        return accountActivityRepository.save(accountActivity);
    }

    public AccountActivity addAccountActivity(CreditCardPaymentResponse creditCardPaymentResponse, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(creditCardPaymentResponse.getAmount());
        accountActivity.setDescription("Pago de tarjeta de credito");
        accountActivity.setAuthorizationCode(creditCardPaymentResponse.getAuthorizationCode());
        accountActivity.setId(1L);

        accountService.updateBalance(
                accountActivity.getAmount().negate(),
                account
        );

        return accountActivityRepository.save(accountActivity);
    }

    public AccountActivity addAccountActivity(MobileCarrierRechargeBuyResponse mobileCarrierRechargeBuyResponse, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(mobileCarrierRechargeBuyResponse.getAmount());
        accountActivity.setDescription(mobileCarrierRechargeBuyResponse.getDescription());
        accountActivity.setAuthorizationCode(mobileCarrierRechargeBuyResponse.getAuthorizationCode());
        accountActivity.setId(1L);

        accountService.updateBalance(
                accountActivity.getAmount().negate(),
                account
        );

        return accountActivityRepository.save(accountActivity);
    }


    public AccountActivity addAccountActivity(TefTransfer tefTransfer, Account account) {
        AccountActivity accountActivity = new AccountActivity();
        accountActivity.setAmount(tefTransfer.getAmount());
        accountActivity.setDescription("Transferencia SPEI");
        accountActivity.setAuthorizationCode(tefTransfer.getAuthorizationCode());
        accountActivity.setId(1L);

        accountService.updateBalance(
                accountActivity.getAmount().negate(),
                account
        );

        return accountActivityRepository.save(accountActivity);
    }

}
