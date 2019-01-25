package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TakeOutMoneyWithoutCard;

import java.math.BigDecimal;

public interface TakeOutMoneyWithoutCardService {
    TakeOutMoneyWithoutCard create(Account originAccount, String description, BigDecimal amount);
}
