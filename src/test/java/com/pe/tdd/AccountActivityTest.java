package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.exception.InvalidAccountException;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.repository.impl.AccountActivityRepositoryImpl;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.impl.AccountActivityServiceImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountActivityTest {

    @Test
    public void shouldReturnAccountActivities() {
        AccountActivityRepository mockAccountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        AccountService mockAccountService = mock(AccountServiceImpl.class);

        AccountActivityService accountActivityService = new AccountActivityServiceImpl(mockAccountActivityRepository, mockAccountService);

        List<AccountActivity> expectedAccountActivityList = Arrays.asList(
                new AccountActivity(123L, "Venta 01", BigDecimal.valueOf(1000), "123"),
                new AccountActivity(456L, "Venta 02", BigDecimal.valueOf(550), "456"),
                new AccountActivity(789L, "Deposito", BigDecimal.valueOf(8000), "789")
        );
        Account expectedAccount = new Account("1234", "Debit 01", BigDecimal.valueOf(5000));

        // Stub
        when(mockAccountActivityRepository.findActivitiesByAccount(anyLong())).thenReturn(expectedAccountActivityList);

        // Stub
        when(mockAccountService.findAccount(anyLong(), anyString())).thenReturn(expectedAccount);

        List<AccountActivity> accountActivities = accountActivityService.findActivitiesByAccount(987456L, "user");

        assertTrue(accountActivities.size() > 0);
    }

    @Test(expected = InvalidAccountException.class)
    public void throwInvalidAccountExceptionOnInvalidAccountNumber() {
        AccountActivityRepositoryImpl mockAccountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        AccountRepositoryImpl mockAccountRepository = mock(AccountRepositoryImpl.class);

        AccountServiceImpl accountService = new AccountServiceImpl(mockAccountRepository);
        AccountActivityServiceImpl accountActivityService = new AccountActivityServiceImpl(mockAccountActivityRepository, accountService);

        // Stub
        when(mockAccountRepository.findAccountByAccountNumberAndUserName(anyLong(), anyString())).thenReturn(null);

        accountActivityService.findActivitiesByAccount(987456L, "user");
    }

}
