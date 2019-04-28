package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.SpeiTransfer;
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

        Account originAccount = new Account("1234", "Debit", initialBalance);

        SpeiTransfer speiTransfer = speiTransferService.transfer(
                originAccount,
                "987654",
                serviceAmount
        );

        LocalDate currentDay = LocalDate.now();

        assertTrue(StringUtils.isEmpty(speiTransfer.getAuthorizationCode()) == false);
        assertTrue(speiTransfer.getOperationDate().equals(currentDay));
    }

}
