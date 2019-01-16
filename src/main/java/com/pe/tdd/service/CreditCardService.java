package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.CreditCardPaymentResponse;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.CreditCardRepository;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardService {

    AccountActivityService accountActivityService;
    AccountService accountService;

    public CreditCardService(AccountActivityService accountActivityService, AccountService accountService) {
        this.accountActivityService = accountActivityService;
        this.accountService = accountService;
    }


    public CreditCardPaymentResponse pay(Account originAccount, String creditCardNumber, BigDecimal amount) {
        if (originAccount == null) {
            throw new IllegalArgumentException("La cuenta origen es requerida");
        }
        if (StringUtils.isEmpty(creditCardNumber)) {
            throw new IllegalArgumentException("El numero de tarjeta es requerido");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a $0.00 Pesos");
        } else if (amount.compareTo(BigDecimal.valueOf(5000)) > 0) {
            throw new IllegalArgumentException("El monto no debe superar los $5000.00 Pesos");
        }

        if (originAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        CreditCardPaymentResponse result = new CreditCardPaymentResponse(
                "12345678",
                LocalDate.now(),
                amount
        );

        accountActivityService.addAccountActivity(result, originAccount);

        return result;
    }
}

