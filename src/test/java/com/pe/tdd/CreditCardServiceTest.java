package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.CreditCardPaymentResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepositoryImpl;
import com.pe.tdd.repository.impl.AccountRepositoryImpl;
import com.pe.tdd.service.impl.AccountActivityServiceImpl;
import com.pe.tdd.service.impl.AccountServiceImpl;
import com.pe.tdd.service.impl.CreditCardServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CreditCardServiceTest {

    private CreditCardServiceImpl creditCardService;

    private AccountActivityRepositoryImpl accountActivityRepository;
    private AccountActivityServiceImpl accountActivityService;

    private AccountRepositoryImpl accountRepository;
    private AccountServiceImpl accountService;

    @Before
    public void setUp() throws Exception {
        accountRepository = mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepository);

        accountActivityRepository = mock(AccountActivityRepositoryImpl.class);
        accountActivityService = new AccountActivityServiceImpl(accountActivityRepository, accountService);

        creditCardService = new CreditCardServiceImpl(accountActivityService, accountService);
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
