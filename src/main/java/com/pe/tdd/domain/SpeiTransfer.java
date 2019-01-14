package com.pe.tdd.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SpeiTransfer {

    private String authorizationCode;
    private LocalDate operationDate;
    private BigDecimal amount;

    public SpeiTransfer(String authorizationCode, LocalDate operationDate, BigDecimal amount) {
        this.authorizationCode = authorizationCode;
        this.operationDate = operationDate;
        this.amount = amount;

    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
