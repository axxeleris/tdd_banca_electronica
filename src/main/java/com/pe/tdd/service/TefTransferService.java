package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TefTransfer;

import java.math.BigDecimal;

public interface TefTransferService {
    TefTransfer transfer(Account originAccount, String accountNumber, BigDecimal amount);
}
