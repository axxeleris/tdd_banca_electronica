package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.CreditCardPaymentResponse;

import java.math.BigDecimal;

public interface CreditCardService {
    CreditCardPaymentResponse pay(Account originAccount, String creditCardNumber, BigDecimal amount);
}
