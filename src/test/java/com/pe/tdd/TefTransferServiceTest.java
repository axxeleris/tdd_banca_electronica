package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TefTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepositoryImpl;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.impl.AccountActivityServiceImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import com.pe.tdd.service.impl.TefTransferServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TefTransferServiceTest {

    private TefTransferServiceImpl tefTransferService;

    private AccountActivityRepositoryImpl accountActivityRepository;
    private AccountActivityServiceImpl accountActivityService;

    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);

        accountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        accountActivityService = new AccountActivityServiceImpl(accountActivityRepository, accountService);

        tefTransferService = new TefTransferServiceImpl(accountActivityService, accountService);
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
