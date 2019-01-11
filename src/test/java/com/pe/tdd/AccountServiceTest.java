package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    @Test
    public void shouldReturnAccountsForUser() {
        AccountRepository mockAccountRepository = mock(AccountRepository.class);

        AccountService accountService = new AccountService(mockAccountRepository);

        List<Account> expectedAccountList = Arrays.asList(
                new Account("1234", "Debit 01", BigDecimal.valueOf(1000)),
                new Account("5678", "Credit 02", BigDecimal.valueOf(5000))
        );

        when(mockAccountRepository.findAccountsByUserName(anyString())).thenReturn(expectedAccountList);

        List<Account> accountsList = accountService.getAccounts("usuario");

        assertTrue(accountsList.size() > 0);
    }



}
