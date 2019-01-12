package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.SpeiTransfer;
import com.pe.tdd.domain.TefTransfer;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.service.SpeiTransferService;
import com.pe.tdd.service.TefTransferService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class SpeiTransferServiceTest {

    private SpeiTransferService speiTransferService;

    @Before
    public void setUp() throws Exception {
        speiTransferService = new SpeiTransferService();
    }

    @Test
    public void shouldDoTransfer() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(1000));

        SpeiTransfer speiTransfer = speiTransferService.transfer(
                originAccount,
                "987654",
                BigDecimal.valueOf(500)
        );

        LocalDate currentDay = LocalDate.now();

        assertTrue(StringUtils.isEmpty(speiTransfer.getAuthorizationCode()) == false);
        assertTrue(speiTransfer.getOperationDate().equals(currentDay));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullOriginAccount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                null,
                "7894564",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullAccountNumber() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                null,
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnEmptyAccountNumber() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNegativeAmount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(-500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnHigherMaxAmount() {
        SpeiTransfer transfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(5001)
        );
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwInsufficientBalanceExceptionOnAmountHigherThanAccountBalance() {
        SpeiTransfer tefTransfer = speiTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(1001)
        );
    }

}
