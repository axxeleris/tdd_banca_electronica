package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.exception.InvalidAccountException;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
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
        AccountActivityRepository mockAccountActivityRepository = mock(AccountActivityRepository.class);
        AccountService mockAccountService = mock(AccountService.class);

        AccountActivityService accountActivityService = new AccountActivityService(mockAccountActivityRepository, mockAccountService);

        List<AccountActivity> expectedAccountActivityList = Arrays.asList(
                new AccountActivity(123L, "Venta 01", BigDecimal.valueOf(1000), "123"),
                new AccountActivity(456L, "Venta 02", BigDecimal.valueOf(550), "456"),
                new AccountActivity(789L, "Deposito", BigDecimal.valueOf(8000), "789")
        );
        Account expectedAccount = new Account("1234", "Debit 01", BigDecimal.valueOf(5000));

        when(mockAccountActivityRepository.findActivitiesByAccount(anyLong())).thenReturn(expectedAccountActivityList);
        when(mockAccountService.findAccount(anyLong(), anyString())).thenReturn(expectedAccount);

        List<AccountActivity> accountActivities = accountActivityService.findActivitiesByAccount(987456L, "user");

        assertTrue(accountActivities.size() > 0);
    }

    @Test(expected = InvalidAccountException.class)
    public void throwInvalidAccountExceptionOnInvalidAccountNumber() {
        AccountActivityRepository mockAccountActivityRepository = mock(AccountActivityRepository.class);
        AccountRepository mockAccountRepository = mock(AccountRepository.class);

        AccountService accountService = new AccountService(mockAccountRepository);
        AccountActivityService accountActivityService = new AccountActivityService(mockAccountActivityRepository, accountService);

        when(mockAccountRepository.findAccountByAccountNumberAndUserName(anyLong(), anyString())).thenReturn(null);

        accountActivityService.findActivitiesByAccount(987456L, "user");
    }

}
