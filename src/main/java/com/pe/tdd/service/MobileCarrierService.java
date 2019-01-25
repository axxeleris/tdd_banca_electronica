package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.MobileCarrier;
import com.pe.tdd.domain.MobileCarrierRecharge;
import com.pe.tdd.domain.MobileCarrierRechargeBuyResponse;

import java.util.List;

public interface MobileCarrierService {
    List<MobileCarrier> findMobileCarriers();

    MobileCarrierRechargeBuyResponse buy(Account originAccount, MobileCarrier mobileCarrier, MobileCarrierRecharge mobileCarrierRecharge);

    List<MobileCarrierRecharge> getRechargesFromCarrier(Long mobileCarrierId);
}
