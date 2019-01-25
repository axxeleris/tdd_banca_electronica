package com.pe.tdd.repository;

import com.pe.tdd.domain.MobileCarrierRecharge;

import java.util.List;

public interface MobileCarrierRechargeRepository {
    List<MobileCarrierRecharge> findAllByMobileCarrierId(Long mobileCarrierId);
}
