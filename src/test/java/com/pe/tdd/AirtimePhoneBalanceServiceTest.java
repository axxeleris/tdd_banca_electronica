package com.pe.tdd;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.mockito.Mockito;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.PhoneOperator;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.PhoneAirtimeBalanceService;

public class AirtimePhoneBalanceServiceTest {

	@Test
	public void shouldBeAbleToBuyAirtimeBalance() {
	    Account account = new Account("123", "Debit01", BigDecimal.valueOf(1000));
	    Account expectedAccount = new Account("123", "Debit01", BigDecimal.valueOf(900));
	    
	    AccountRepository mockAccountRepository = mock(AccountRepository.class);
	    PhoneAirtimeBalanceService phoneAirtimeBalanceService = new PhoneAirtimeBalanceService(mockAccountRepository);
	    
	    
	    when(mockAccountRepository.save(Mockito.anyObject())).thenReturn(expectedAccount);
	    account = phoneAirtimeBalanceService.buy(account, PhoneOperator.TELCEL, BigDecimal.valueOf(100));
	    
	    assertTrue(expectedAccount.getBalance().intValue() == 900);
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void throwInsufficientBalanceExceptionWhenBuyingWithoutEnoughBalance() {
	    Account account = new Account("123", "Debit01", BigDecimal.valueOf(0));
	    Account expectedAccount = new Account("123", "Debit01", BigDecimal.valueOf(900));
	    
	    AccountRepository mockAccountRepository = mock(AccountRepository.class);
	    PhoneAirtimeBalanceService phoneAirtimeBalanceService = new PhoneAirtimeBalanceService(mockAccountRepository);
	    
	    
	    when(mockAccountRepository.save(Mockito.anyObject())).thenReturn(expectedAccount);
	    account = phoneAirtimeBalanceService.buy(account, PhoneOperator.TELCEL, BigDecimal.valueOf(100));
	}
}
