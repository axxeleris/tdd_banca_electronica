package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    AccountRepositoryImpl accountRepository;
    AccountServiceImpl accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void shouldReturnAccountsForUser() {
        List<Account> expectedAccountList = Arrays.asList(
                new Account("1234", "Debit 01", BigDecimal.valueOf(1000)),
                new Account("5678", "Credit 02", BigDecimal.valueOf(5000))
        );

        when(accountRepository.findAccountsByUserName(anyString())).thenReturn(expectedAccountList);

        List<Account> accountsList = accountService.getAccounts("usuario");

        assertTrue(accountsList.size() > 0);
    }


}
