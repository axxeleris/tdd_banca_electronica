package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NipServiceTest {

    AccountRepository accountRepository;
    AccountService accountService;

    @Before
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
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
    public void throwIlegalExceptionOnChangeNipWithInvalidNewNipLength() {
        String accountNumber = "1234567890";
        String newNip = "12";
        String actualNip = "4321";

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalExceptionOnChangeNipWithInvalidActualNip() {
        String accountNumber = "1234567890";
        String newNip = "1234";
        String actualNip = null;

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalExceptionOnChangeNipWithInvalidAccountNumber() {
        String accountNumber = null;
        String newNip = "1234";
        String actualNip = "4321";

        accountService.changeNip(newNip, actualNip, accountNumber);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalExceptionOnChangeNipWithInvalidAccount() {
        String accountNumber = "1234567890";
        String newNip = "1234";
        String actualNip = "4321";

        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(null);

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
