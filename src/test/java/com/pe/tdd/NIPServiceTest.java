package com.pe.tdd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.pe.tdd.domain.CreditCard;
import com.pe.tdd.exception.NIPUpdateException;
import com.pe.tdd.exception.NIPUpdateMissingCharactersException;
import com.pe.tdd.repository.CreditCardRepository;
import com.pe.tdd.service.CreditCardService;

public class NIPServiceTest {

	private static int fourDigitNIP = 1234;
	private static CreditCard expectedCreditCardNIPChange;
	
    @BeforeClass 
    public static void setUpClass() { 
    	expectedCreditCardNIPChange = new CreditCard();
    	expectedCreditCardNIPChange.setNIP(fourDigitNIP);
	}
    
	@Test
	public void shouldChangeNIPWhenConfirmIsCorrect() {
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class);
		CreditCardService cardService = new CreditCardService(mockCreditCardRepository);
		
		when(mockCreditCardRepository.updateNip(Mockito.anyInt()))
						.thenReturn(expectedCreditCardNIPChange);
		
		CreditCard changedNIP = cardService.updateNip(fourDigitNIP, fourDigitNIP);
		assertEquals(changedNIP, expectedCreditCardNIPChange);
		
	}

	@Test(expected = NIPUpdateException.class)
	public void shouldNotChangeNIPWhenConfirmIsIncorrect() {		
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class);
		CreditCardService cardService = new CreditCardService(mockCreditCardRepository);
		
		when(mockCreditCardRepository.updateNip(Mockito.anyInt()))
						.thenReturn(expectedCreditCardNIPChange);
		
		cardService.updateNip(fourDigitNIP, fourDigitNIP + 1);
	}
	
	@Test(expected = NIPUpdateMissingCharactersException.class)
	public void shouldNotChangeNIPWhenLessThanFourDigitsGiven() {		
		int threeDigitNIP = 123;
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class);
		CreditCardService cardService = new CreditCardService(mockCreditCardRepository);
		
		when(mockCreditCardRepository.updateNip(Mockito.anyInt()))
						.thenReturn(expectedCreditCardNIPChange);
		
		cardService.updateNip(threeDigitNIP, fourDigitNIP);
	}
}
