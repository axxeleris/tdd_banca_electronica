package com.pe.tdd;


import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TefTransfer;
import com.pe.tdd.service.TefTransferService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class TefTransferServiceTest {

    private TefTransferService tefTransferService;

    @Before
    public void setUp() throws Exception {
        tefTransferService = new TefTransferService();
    }

    @Test
    public void shouldDoTransfer() {
        Account originAccount = new Account("1234", "Debit", BigDecimal.valueOf(1000));

        TefTransfer tefTransfer = tefTransferService.transfer(
                originAccount,
                "987654",
                BigDecimal.valueOf(500)
        );

        LocalDate nextDay = LocalDate.now().plusDays(1);

        assertTrue(StringUtils.isEmpty(tefTransfer.getAuthorizationCode()) == false);
        assertTrue(tefTransfer.getOperationDate().equals(nextDay));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullOriginAccount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                null,
                "7894564",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNullAccountNumber() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                null,
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnEmptyAccountNumber() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "",
                BigDecimal.valueOf(500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnNegativeAmount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(-500)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionOnHigherMaxAmount() {
        TefTransfer tefTransfer = tefTransferService.transfer(
                new Account("1234", "Debit", BigDecimal.valueOf(1000)),
                "879465",
                BigDecimal.valueOf(5001)
        );
    }

}
