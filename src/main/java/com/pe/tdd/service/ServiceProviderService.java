package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ServiceProvider;
import com.pe.tdd.domain.ServiceProviderPaymentResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.ServiceProviderRepository;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;

    private final AccountActivityService accountActivityService;

    public ServiceProviderService(ServiceProviderRepository serviceProviderRepository, AccountActivityService accountActivityService) {
        this.serviceProviderRepository = serviceProviderRepository;
        this.accountActivityService = accountActivityService;
    }

    public List<ServiceProvider> findServiceProviders() {
        return serviceProviderRepository.findAll();
    }

    public ServiceProviderPaymentResponse pay(Account originAccount, ServiceProvider serviceProvider, String reference, LocalDate date, BigDecimal amount) {
        if (StringUtils.isEmpty(reference)) {
            throw new IllegalArgumentException();
        }

        if (serviceProvider == null) {
            throw new IllegalArgumentException("El proveedor de servicio es requerido");
        }

        if (date == null) {
            throw new IllegalArgumentException("La fecha es requerida");
        }

        if (amount == null) {
            throw new IllegalArgumentException("El monto es requerido");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        if (date.compareTo(LocalDate.now()) < 0) {
            throw new IllegalArgumentException("No se pueden pagar recibos con fecha no vigentes");
        }

        if (originAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        ServiceProviderPaymentResponse response = new ServiceProviderPaymentResponse(
                "1234",
                "Pago de servicio: " + serviceProvider.getDescription(),
                LocalDate.now(),
                amount
        );

        accountActivityService.addAccountActivity(response, originAccount);

        return response;
    }

}
