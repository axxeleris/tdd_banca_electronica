package com.pe.tdd.service;

import com.pe.tdd.domain.Account;
import com.pe.tdd.domain.TakeOutMoneyWithoutCard;
import com.pe.tdd.exception.InsufficientBalanceException;
import com.pe.tdd.repository.impl.TakeOutMoneyWithoutCardRepository;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TakeOutMoneyWithoutCardService {

    private final TakeOutMoneyWithoutCardRepository takeOutMoneyWithoutCardRepository;

    public TakeOutMoneyWithoutCardService(TakeOutMoneyWithoutCardRepository takeOutMoneyWithoutCardRepository) {
        this.takeOutMoneyWithoutCardRepository = takeOutMoneyWithoutCardRepository;
    }

    public TakeOutMoneyWithoutCard create(Account originAccount, String description, BigDecimal amount) {
        if (originAccount == null) {
            throw new IllegalArgumentException("La cuenta origen es requerida");
        }
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("La descripcion es requerida");
        }
        if(amount == null) {
            throw new IllegalArgumentException("El monto es requerido");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto no es valido");
        }
        if (originAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        TakeOutMoneyWithoutCard takeOutMoneyWithoutCard = new TakeOutMoneyWithoutCard();
        takeOutMoneyWithoutCard.setAmount(amount);
        takeOutMoneyWithoutCard.setCode("123456");
        takeOutMoneyWithoutCard.setDate(LocalDate.now());
        takeOutMoneyWithoutCard.setDescription(description);
        takeOutMoneyWithoutCard.setId(1L);

        return takeOutMoneyWithoutCardRepository.save(takeOutMoneyWithoutCard);
    }
}
