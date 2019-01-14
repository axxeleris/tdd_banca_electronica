package com.pe.tdd.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MobileCarrierRechargeBuyResponse {
    String authorizationCode;
    String description;
    LocalDate operationDate;
    BigDecimal amount;

    public MobileCarrierRechargeBuyResponse(String authorizationCode, String description, LocalDate operationDate, BigDecimal amount) {
        this.authorizationCode = authorizationCode;
        this.description = description;
        this.operationDate = operationDate;
        this.amount = amount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
