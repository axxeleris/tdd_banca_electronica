package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.CreditCardPaymentResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.AccountActivityRepository;
import com.pe.tdd.repository.AccountRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.CreditCardService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CreditCardServiceTest {

    CreditCardService creditCardService;

    private AccountActivityRepository accountActivityRepository;
    private AccountActivityService accountActivityService;

    private AccountRepository accountRepository;
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);

        accountActivityRepository = mock(AccountActivityRepository.class);
        accountActivityService = new AccountActivityService(accountActivityRepository, accountService);

        creditCardService = new CreditCardService(accountActivityService, accountService);
    }

    @Test
    public void shouldPayCreditCard() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal paymentAmount = BigDecimal.valueOf(150);
        BigDecimal expectedBalance = initialBalance.subtract(paymentAmount);

        String creditCardNumber = "1234567890123456";

        Account originAccount = new Account("123456", "Debit", initialBalance);

        CreditCardPaymentResponse creditCardPaymentResponse = creditCardService.pay(originAccount, creditCardNumber, paymentAmount);

        assertTrue(StringUtils.isEmpty(creditCardPaymentResponse.getAuthorizationCode()) == false);
        assertTrue(expectedBalance.compareTo(originAccount.getBalance()) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullOriginAccount() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                null,
                "7894564",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullAccountNumber() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                null,
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnEmptyAccountNumber() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNegativeAmount() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(-500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnHigherMaxAmount() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(5001)
        );
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwInsufficientBalanceExceptionOnAmountHigherThanAccountBalance() {
        CreditCardPaymentResponse payment = creditCardService.pay(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(1001)
        );
    }

}
