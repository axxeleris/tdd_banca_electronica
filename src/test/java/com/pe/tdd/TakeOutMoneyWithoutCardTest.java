package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TakeOutMoneyWithoutCard;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.TakeOutMoneyWithoutCardRepositoryImpl;
import com.pe.tdd.service.impl.TakeOutMoneyWithoutCardServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TakeOutMoneyWithoutCardTest {

    TakeOutMoneyWithoutCardRepositoryImpl takeOutMoneyWithoutCardRepository;

    TakeOutMoneyWithoutCardServiceImpl takeOutMoneyWithoutCardService;

    @Before
    public void setUp() throws Exception {
        takeOutMoneyWithoutCardRepository = mock(TakeOutMoneyWithoutCardRepositoryImpl.class);
        takeOutMoneyWithoutCardService = new TakeOutMoneyWithoutCardServiceImpl(takeOutMoneyWithoutCardRepository);
    }

    @Test
    public void createTakeOutMoneyWithoutCard() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(1000));

        TakeOutMoneyWithoutCard expectedResult = new TakeOutMoneyWithoutCard();
        expectedResult.setId(1L);
        expectedResult.setDescription("Test");
        expectedResult.setDate(LocalDate.now());
        expectedResult.setCode("1234567890");
        expectedResult.setAmount(BigDecimal.valueOf(100));

        when(takeOutMoneyWithoutCardRepository.save(anyObject())).thenReturn(expectedResult);

        TakeOutMoneyWithoutCard takeOutMoneyWithoutCard = takeOutMoneyWithoutCardService.create(account, "Test", BigDecimal.valueOf(100));

        assertNotNull(takeOutMoneyWithoutCard);
        assertNotNull(takeOutMoneyWithoutCard.getCode());
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwIllegalArgumentExceptionOnCreateWithBalanceLessThanAmount() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(50));

        TakeOutMoneyWithoutCard expectedResult = new TakeOutMoneyWithoutCard();
        expectedResult.setId(1L);
        expectedResult.setDescription("Test");
        expectedResult.setDate(LocalDate.now());
        expectedResult.setCode("1234567890");
        expectedResult.setAmount(BigDecimal.valueOf(100));

        when(takeOutMoneyWithoutCardRepository.save(anyObject())).thenReturn(expectedResult);

        takeOutMoneyWithoutCardService.create(account, "Test", BigDecimal.valueOf(100));

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnCreateWithOriginAccountNull() {
        takeOutMoneyWithoutCardService.create(null, "Test", BigDecimal.valueOf(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnCreateWithDescriptionNull() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(500));
        takeOutMoneyWithoutCardService.create(account, null, BigDecimal.valueOf(100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnCreateWithAmountNull() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(500));
        takeOutMoneyWithoutCardService.create(account, "Test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnCreateWithAmountIsZero() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(500));
        takeOutMoneyWithoutCardService.create(account, "Test", BigDecimal.ZERO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnCreateWithAmountLessThanZero() {
        Account account = new Account("123456", "Debit 01", BigDecimal.valueOf(500));
        takeOutMoneyWithoutCardService.create(account, "Test", BigDecimal.valueOf(-10));
    }

}
