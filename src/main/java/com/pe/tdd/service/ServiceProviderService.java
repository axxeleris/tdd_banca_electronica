package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ServiceProvider;
import com.pe.tdd.domain.ServiceProviderPaymentResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ServiceProviderService {
    List<ServiceProvider> findServiceProviders();

    ServiceProviderPaymentResponse pay(Account originAccount, ServiceProvider serviceProvider, String reference, LocalDate date, BigDecimal amount);
}
