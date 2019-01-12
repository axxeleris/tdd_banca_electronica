package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TefTransfer;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TefTransferService {

    public TefTransfer transfer(Account originAccount, String accountNumber, BigDecimal amount) {
        if (originAccount == null) {
            throw new IllegalArgumentException("originAccount");
        }
        if (StringUtils.isEmpty(accountNumber)) {
            throw new IllegalArgumentException("accountNumber");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a $0.00 Pesos");
        } else if (amount.compareTo(BigDecimal.valueOf(5000)) > 0) {
            throw new IllegalArgumentException("El monto no debe superar los $5000.00 Pesos");
        }

        return new TefTransfer(
                "12345678",
                LocalDate.now().plusDays(1)
        );
    }


}
