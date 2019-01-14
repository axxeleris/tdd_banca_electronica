package com.pe.tdd.domain;

import java.time.LocalDate;

public class TefTransfer {

    public String authorizationCode;
    public LocalDate operationDate;

    public TefTransfer(String authorizationCode, LocalDate operationDate) {
        this.authorizationCode = authorizationCode;
        this.operationDate = operationDate;
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
}