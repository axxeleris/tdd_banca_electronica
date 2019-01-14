package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TefTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.TefTransferService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TefTransferServiceTest {

    private TefTransferService tefTransferService;

    private AccountActivityRepository accountActivityRepository;
    private AccountActivityService accountActivityService;

    private AccountRepository accountRepository;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);

        accountActivityRepository = mock(AccountActivityRepository.class);
        accountActivityService = new AccountActivityService(accountActivityRepository, accountService);

        tefTransferService = new TefTransferService(accountActivityService, accountService);
    }

    @Test
    public void shouldDoTransfer() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal serviceAmount = BigDecimal.valueOf(150);
        BigDecimal expectedBalance = initialBalance.subtract(serviceAmount);

        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(1000));

        TefTransfer tefTransfer = tefTransferService.transfer(
                originAccount,
                "987654",
                serviceAmount
        );

        LocalDate nextDay = LocalDate.now().plusDays(1);

        assertTrue(StringUtils.isEmpty(tefTransfer.getAuthorizationCode()) == false);
        assertTrue(tefTransfer.getOperationDate().equals(nextDay));
        assertTrue(expectedBalance.compareTo(originAccount.getBalance()) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullOriginAccount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                null,
                "7894564",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullAccountNumber() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                null,
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnEmptyAccountNumber() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNegativeAmount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(-500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnHigherMaxAmount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(5001)
        );
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwInsufficientBalanceExceptionOnAmountHigherThanAccountBalance() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(1001)
        );
    }

}
