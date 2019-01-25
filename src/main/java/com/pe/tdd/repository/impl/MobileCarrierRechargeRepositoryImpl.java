package com.pe.tdd.repository.impl;

import com.pe.tdd.domain.MobileCarrierRecharge;
import com.pe.tdd.repository.MobileCarrierRechargeRepository;

import java.util.Collections;
import java.util.List;

public class MobileCarrierRechargeRepositoryImpl implements MobileCarrierRechargeRepository {

    @Override
    public List<MobileCarrierRecharge> findAllByMobileCarrierId(Long mobileCarrierId) {
        return Collections.emptyList();
    }

}
