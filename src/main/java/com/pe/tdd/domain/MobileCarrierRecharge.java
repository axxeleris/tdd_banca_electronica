package com.pe.tdd.domain;

import java.math.BigDecimal;

public class MobileCarrierRecharge {
    private Long id;
    private String description;
    private BigDecimal amount;

    public MobileCarrierRecharge(Long id, String description, BigDecimal amount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
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
}
