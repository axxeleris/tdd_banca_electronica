package com.pe.tdd;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.MobileCarrier;
import com.pe.tdd.domain.MobileCarrierRecharge;
import com.pe.tdd.domain.MobileCarrierRechargeBuyResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.AccountActivityRepository;
import com.pe.tdd.repository.impl.AccountRepository;
import com.pe.tdd.repository.impl.MobileCarrierRechargeRepository;
import com.pe.tdd.repository.impl.MobileCarrierRepository;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.MobileCarrierService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MobileCarrierServiceTest {

    private MobileCarrierService mobileCarrierService;
    private MobileCarrierRepository mobileCarrierRepository;
    private MobileCarrierRechargeRepository mobileCarrierRechargeRepository;

    private AccountActivityRepository accountActivityRepository;
    private AccountActivityService accountActivityService;

    private AccountRepository accountRepository;
    private AccountService accountService;

    @Before
    public void setUp() {

        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);

        accountActivityRepository = mock(AccountActivityRepository.class);
        accountActivityService = new AccountActivityService(accountActivityRepository, accountService);

        mobileCarrierRechargeRepository = mock(MobileCarrierRechargeRepository.class);
        mobileCarrierRepository = mock(MobileCarrierRepository.class);
        mobileCarrierService = new MobileCarrierService(
                mobileCarrierRepository,
                mobileCarrierRechargeRepository,
                accountActivityService,
                accountService
        );

        when(mobileCarrierRepository.findAll()).thenReturn(
                Arrays.asList(
                        new MobileCarrier(1L, "Telcel"),
                        new MobileCarrier(1L, "Movistar"),
                        new MobileCarrier(1L, "Att&t")
                )
        );

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalArgumentExceptionOnGetRechargesFromCarrierWhenMobileCarrierIdIsNull() {
        mobileCarrierService.getRechargesFromCarrier(null);
    }

    @Test
    public void shouldBuyMobileCarrierRecharge() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal carrierRechargeAmount = BigDecimal.valueOf(200);
        BigDecimal expectedBalance = initialBalance.subtract(carrierRechargeAmount);

        Account originAccount = new Account("123", "Debit01", initialBalance);

        MobileCarrier mobileCarrier = mobileCarrierService.findMobileCarriers().stream()
                .findFirst()
                .get();

        when(mobileCarrierRechargeRepository.findAllByMobileCarrierId(anyLong())).thenReturn(
                Arrays.asList(
                        new MobileCarrierRecharge(1L, "Movistar $200", carrierRechargeAmount)
                )
        );
        MobileCarrierRecharge mobileCarrierRecharge = mobileCarrierService.getRechargesFromCarrier(mobileCarrier.getId()).stream()
                .findFirst()
                .get();

        MobileCarrierRechargeBuyResponse response = mobileCarrierService.buy(originAccount, mobileCarrier, mobileCarrierRecharge);

        assertNotNull(response);
        assertTrue(originAccount.getBalance().compareTo(expectedBalance) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalArgumentExceptionOnBuyWithOriginAccountIsNull() {
        BigDecimal carrierRechargeAmount = BigDecimal.valueOf(200);

        Account originAccount = null;

        MobileCarrier mobileCarrier = mobileCarrierService.findMobileCarriers().stream()
                .findFirst()
                .get();

        when(mobileCarrierRechargeRepository.findAllByMobileCarrierId(anyLong())).thenReturn(
                Arrays.asList(
                        new MobileCarrierRecharge(1L, "Movistar $200", carrierRechargeAmount)
                )
        );
        MobileCarrierRecharge mobileCarrierRecharge = mobileCarrierService.getRechargesFromCarrier(mobileCarrier.getId()).stream()
                .findFirst()
                .get();

        mobileCarrierService.buy(originAccount, mobileCarrier, mobileCarrierRecharge);

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalArgumentExceptionOnBuyWithMobileCarrierIsNull() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal carrierRechargeAmount = BigDecimal.valueOf(200);

        Account originAccount = new Account("123", "Debit01", initialBalance);

        MobileCarrier mobileCarrier = new MobileCarrier(1L, "Test");

        MobileCarrierRecharge mobileCarrierRecharge = null;

        mobileCarrierService.buy(originAccount, mobileCarrier, mobileCarrierRecharge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIlegalArgumentExceptionOnBuyWithMobileCarrieRechargeIsNull() {
        BigDecimal initialBalance = BigDecimal.valueOf(1000);
        BigDecimal carrierRechargeAmount = BigDecimal.valueOf(200);

        Account originAccount = new Account("123", "Debit01", initialBalance);

        MobileCarrierRecharge mobileCarrierRecharge = new MobileCarrierRecharge(1L, "Test", BigDecimal.ZERO);

        mobileCarrierService.buy(originAccount, null, mobileCarrierRecharge);
    }

    @Test(expected = InsufficientBalanceException.class)
    public void throwInsufficientBalanceExceptionOnBuyWhenBuyingWithoutEnoughBalance() {
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        BigDecimal carrierRechargeAmount = BigDecimal.valueOf(200);
        BigDecimal expectedBalance = initialBalance.subtract(carrierRechargeAmount);

        Account originAccount = new Account("123", "Debit01", initialBalance);

        MobileCarrier mobileCarrier = mobileCarrierService.findMobileCarriers().stream()
                .findFirst()
                .get();

        when(mobileCarrierRechargeRepository.findAllByMobileCarrierId(anyLong())).thenReturn(
                Arrays.asList(
                        new MobileCarrierRecharge(1L, "Movistar $200", carrierRechargeAmount)
                )
        );
        MobileCarrierRecharge mobileCarrierRecharge = mobileCarrierService.getRechargesFromCarrier(mobileCarrier.getId()).stream()
                .findFirst()
                .get();

        mobileCarrierService.buy(originAccount, mobileCarrier, mobileCarrierRecharge);
    }
}
