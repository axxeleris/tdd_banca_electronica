package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.SpeiTransfer;

import java.math.BigDecimal;

public interface SpeiTransferService {
    SpeiTransfer transfer(Account originAccount, String accountNumber, BigDecimal amount);
}
