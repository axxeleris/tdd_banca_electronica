package com.pe.tdd.domain;

import java.math.BigDecimal;

public class AccountActivity {
    private Long id;
    private String description;
    private BigDecimal amount;
    private String authorizationCode;

    public AccountActivity() {
    }

    public AccountActivity(Long id, String description, BigDecimal amount, String authorizationCode) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.authorizationCode = authorizationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
