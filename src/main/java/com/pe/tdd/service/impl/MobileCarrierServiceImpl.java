package com.pe.tdd.service.impl;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.MobileCarrier;
import com.pe.tdd.domain.MobileCarrierRecharge;
import com.pe.tdd.domain.MobileCarrierRechargeBuyResponse;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.MobileCarrierRechargeRepository;
import com.pe.tdd.repository.impl.MobileCarrierRepositoryImpl;
import com.pe.tdd.service.AccountActivityService;
import com.pe.tdd.service.AccountService;
import com.pe.tdd.service.MobileCarrierService;

import java.time.LocalDate;
import java.util.List;

public class MobileCarrierServiceImpl implements MobileCarrierService {

    MobileCarrierRepositoryImpl mobileCarrierRepository;
    MobileCarrierRechargeRepository mobileCarrierRechargeRepository;
    AccountActivityService accountActivityService;
    AccountService accountService;

    public MobileCarrierServiceImpl(MobileCarrierRepositoryImpl mobileCarrierRepository, MobileCarrierRechargeRepository mobileCarrierRechargeRepository, AccountActivityService accountActivityService, AccountService accountService) {
        this.mobileCarrierRepository = mobileCarrierRepository;
        this.mobileCarrierRechargeRepository = mobileCarrierRechargeRepository;
        this.accountActivityService = accountActivityService;
        this.accountService = accountService;
    }

    @Override
    public List<MobileCarrier> findMobileCarriers() {
        return mobileCarrierRepository.findAll();
    }

    @Override
    public MobileCarrierRechargeBuyResponse buy(Account originAccount, MobileCarrier mobileCarrier, MobileCarrierRecharge mobileCarrierRecharge) {
        if (originAccount == null) {
            throw new IllegalArgumentException("La cuenta de origen es requerida");
        }
        if (mobileCarrier == null) {
            throw new IllegalArgumentException("El carrier o compañia es requerido");
        }
        if (mobileCarrierRecharge == null) {
            throw new IllegalArgumentException("El valor de la recaga es requerido");
        }
        if (originAccount.getBalance().compareTo(mobileCarrierRecharge.getAmount()) < 0) {
            throw new InsufficientBalanceException();
        }

        MobileCarrierRechargeBuyResponse result = new MobileCarrierRechargeBuyResponse(
                "1234",
                "Recarga de tiempo aire",
                LocalDate.now(),
                mobileCarrierRecharge.getAmount()
        );

        accountActivityService.addAccountActivity(result, originAccount);

        return result;
    }

    @Override
    public List<MobileCarrierRecharge> getRechargesFromCarrier(Long mobileCarrierId) {
        if (mobileCarrierId == null) {
            throw new IllegalArgumentException("El identificador del carrier o compañia es requerido");
        }
        return mobileCarrierRechargeRepository.findAllByMobileCarrierId(mobileCarrierId);
    }
}
