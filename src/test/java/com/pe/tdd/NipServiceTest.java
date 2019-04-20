package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NipServiceTest {

    AccountRepositoryImpl accountRepository;
    AccountServiceImpl accountService;

    @Before
    public void setUp() {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void shouldChangeNip() {
        String accountNumber = "1234567890";
        String newNip = "1234";
        String actualNip = "4321";

        Account account = new Account(accountNumber, "Debit", BigDecimal.valueOf(1000));
        account.setNip(actualNip);

        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(account);

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalExceptionOnChangeNipWithNullNewNip() {
        String accountNumber = "1234567890";
        String newNip = null;
        String actualNip = "4321";

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalExceptionOnChangeNipWithActualNipDoesntMatch() {
        String accountNumber = "1234567890";
        String newNip = "1234";
        String actualNip = "4321";

        Account account = new Account(accountNumber, "Debit", BigDecimal.valueOf(1000));
        account.setNip("9876");

        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(account);

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

}
