package com.pe.tdd.service;

import com.pe.tdd.domain.CreditCard;
import com.pe.tdd.exception.NipUpdateException;
import com.pe.tdd.exception.NipUpdateMissingCharactersException;
import com.pe.tdd.repository.CreditCardRepository;

public class CreditCardService {

	private final CreditCardRepository creditCardRepository;
	
	public CreditCardService(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}
	
	public CreditCard updateNip(String nip, String nipConfirm) {
		if(nip.length() < 4) {
			throw new NipUpdateMissingCharactersException();
		}
		if(nip == nipConfirm) {
			return creditCardRepository.updateNip(nip);
		}else {
			throw new NipUpdateException();
		}
	}
}
