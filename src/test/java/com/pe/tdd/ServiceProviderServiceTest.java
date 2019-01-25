package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.ServiceProvider;
import com.pe.tdd.domain.ServiceProviderPaymentResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepositoryImpl;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.repository.impl.ServiceProviderRepositoryImpl;
import com.pe.tdd.service.impl.AccountActivityServiceImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import com.pe.tdd.service.impl.ServiceProviderServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServiceProviderServiceTest {

    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;
    private AccountActivityRepositoryImpl accountActivityRepository;
    private AccountActivityServiceImpl accountActivityService;
    private ServiceProviderServiceImpl serviceProviderService;
    private ServiceProviderRepositoryImpl serviceProviderRepository;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);

        accountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        accountActivityService = new AccountActivityServiceImpl(accountActivityRepository, accountService);

        serviceProviderRepository = mock(ServiceProviderRepositoryImpl.class);
        serviceProviderService = new ServiceProviderServiceImpl(serviceProviderRepository, accountActivityService);

        when(serviceProviderRepository.findAll()).thenReturn(
                Arrays.asList(
                        new ServiceProvider(1L, "CFE"),
                        new ServiceProvider(2L, "Agua y Drenaje"),
                        new ServiceProvider(3L, "Gas Natural")
                )
        );
    }

    @Test
    public void shouldGetServiceProvidersList() {
        List<ServiceProvider> serviceProviders = serviceProviderService.findServiceProviders();

        assertNotNull(serviceProviders);
        assertTrue(serviceProviders.size() > 0);
    }

    @Test
    public void shouldPayService() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal serviceAmount = BigDecimal.valueOf(150);
        BigDecimal expectedBalance = initialBalance.subtract(serviceAmount);

        Account originAccount = new Account("1234", "Debit", initialBalance);

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                LocalDate.now(),
                serviceAmount
        );

        assertNotNull(paymentServiceResponse.getAuthorizationCode());
        assertTrue(originAccount.getBalance().compareTo(expectedBalance) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenDateIsBeforeNow() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(1000));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                LocalDate.now().minusDays(5),
                BigDecimal.valueOf(150)
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenReferenceIsNull() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(1000));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                null,
                LocalDate.now().minusDays(5),
                BigDecimal.valueOf(150)
        );

    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwIllegalArgumentExceptionWhenAmountIsLessThanAccountBalance() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(100));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                LocalDate.now(),
                BigDecimal.valueOf(200)
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throw_IllegalArgumentException_When_ServiceProviderIsNull() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(100));


        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                null,
                "123456",
                LocalDate.now(),
                BigDecimal.valueOf(1)
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenDateIsNull() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(100));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                null,
                BigDecimal.valueOf(1)
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenAmountIsNull() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(100));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                LocalDate.now(),
                null
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionWhenAmountIsLessThanZero() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(100));

        ServiceProvider serviceProvider = serviceProviderService.findServiceProviders().stream()
                .findFirst()
                .get();

        ServiceProviderPaymentResponse paymentServiceResponse = serviceProviderService.pay(
                originAccount,
                serviceProvider,
                "123456",
                LocalDate.now(),
                BigDecimal.valueOf(-5)
        );

    }


}
