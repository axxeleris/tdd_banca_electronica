package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepositoryImpl;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.impl.AccountActivityServiceImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import com.pe.tdd.service.impl.SpeiTransferServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SpeiTransferServiceTest {

    private SpeiTransferServiceImpl speiTransferService;

    private AccountActivityRepositoryImpl accountActivityRepository;
    private AccountActivityServiceImpl accountActivityService;

    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;

    @Before
    public void setUp() {

        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);

        accountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        accountActivityService = new AccountActivityServiceImpl(accountActivityRepository, accountService);

        speiTransferService = new SpeiTransferServiceImpl(accountActivityService, accountService);
    }

    @Test
    public void shouldDoTransfer() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal serviceAmount = BigDecimal.valueOf(150);
        BigDecimal expectedBalance = initialBalance.subtract(serviceAmount);

        Account originAccount = new Account("1234", "Debit", initialBalance);

        SpeiTransfer speiTransfer = speiTransferService.transfer(
                originAccount,
                "987654",
                serviceAmount
        );

        LocalDate currentDay = LocalDate.now();

        assertTrue(StringUtils.isEmpty(speiTransfer.getAuthorizationCode()) == false);
        assertTrue(speiTransfer.getOperationDate().equals(currentDay));
        assertTrue(expectedBalance.compareTo(originAccount.getBalance()) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullOriginAccount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                null,
                "7894564",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullAccountNumber() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                null,
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnEmptyAccountNumber() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNegativeAmount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(-500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnHigherMaxAmount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(5001)
        );
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwInsufficientBalanceExceptionOnAmountHigherThanAccountBalance() {
        SpeiTransfer tefTransfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(1001)
        );
    }

}
