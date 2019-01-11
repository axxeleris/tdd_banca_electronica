package com.pe.tdd.service;

import com.pe.tdd.domain.CreditCard;
import com.pe.tdd.exception.NIPUpdateException;
import com.pe.tdd.exception.NIPUpdateMissingCharactersException;
import com.pe.tdd.repository.CreditCardRepository;

public class CreditCardService {

	private final CreditCardRepository creditCardRepository;
	
	public CreditCardService(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}
	
	public CreditCard updateNip(int nip, int nipConfirm) {
		if(nip < 4) {
			throw new NIPUpdateMissingCharactersException();
		}
		if(nip == nipConfirm) {
			return creditCardRepository.updateNip(nip);
		}else {
			throw new NIPUpdateException();
		}
	}
}
