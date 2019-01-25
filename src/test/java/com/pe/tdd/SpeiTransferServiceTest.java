package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepository;
import com.pe.tdd.repository.impl.AccountRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.SpeiTransferService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SpeiTransferServiceTest {

    private SpeiTransferService speiTransferService;

    private AccountActivityRepository accountActivityRepository;
    private AccountActivityService accountActivityService;

    private AccountRepository accountRepository;
    private AccountService accountService;

    @Before
    public void setUp() {

        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);

        accountActivityRepository = mock(AccountActivityRepository.class);
        accountActivityService = new AccountActivityService(accountActivityRepository, accountService);

        speiTransferService = new SpeiTransferService(accountActivityService, accountService);
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
