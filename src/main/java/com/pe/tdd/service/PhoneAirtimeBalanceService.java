package com.pe.tdd.service;

import java.math.BigDecimal;

import com.pe.tdd.domain.Account;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.AccountRepository;

public class PhoneAirtimeBalanceService {
	
	private AccountRepository accountRepository;
	
	public PhoneAirtimeBalanceService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	public Account buy(Account account, String phoneOperator, BigDecimal amount) {
		BigDecimal totalAmount = account.getBalance().subtract(amount);
		if(totalAmount.doubleValue() < 0 ) {
			throw new InsufficientBalanceException();
		}
		account.setBalance(totalAmount);
		accountRepository.update(account);
		return account;
	}
}
