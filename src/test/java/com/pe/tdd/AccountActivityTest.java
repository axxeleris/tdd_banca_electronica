package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.AccountActivity;
import com.pe.tdd.exception.InvalidAccountException;
import com.pe.tdd.repository.AccountActivityRepository;
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
                new AccountActivity(513354L, "Venta 01", BigDecimal.valueOf(1000)),
                new AccountActivity(48711L, "Venta 02", BigDecimal.valueOf(550)),
                new AccountActivity(864546L, "Deposito", BigDecimal.valueOf(8000))
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
        AccountService mockAccountService = mock(AccountService.class);

        AccountActivityService accountActivityService = new AccountActivityService(mockAccountActivityRepository, mockAccountService);

        when(mockAccountService.findAccount(anyLong(), anyString())).thenReturn(null);

        List<AccountActivity> accountActivities = accountActivityService.findActivitiesByAccount(987456L, "user");
    }

}
