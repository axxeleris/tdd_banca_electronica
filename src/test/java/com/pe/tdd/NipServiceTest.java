package com.pe.tdd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.pe.tdd.domain.CreditCard;
import com.pe.tdd.exception.NipUpdateException;
import com.pe.tdd.exception.NipUpdateMissingCharactersException;
import com.pe.tdd.repository.CreditCardRepository;
import com.pe.tdd.service.CreditCardService;

public class NipServiceTest {

	private static String fourDigitNip = "1234";
	private static CreditCard expectedCreditCardNIPChange;
	
    @BeforeClass 
    public static void setUpClass() { 
    	expectedCreditCardNIPChange = new CreditCard();
    	expectedCreditCardNIPChange.setNip(fourDigitNip);
	}
    
	@Test
	public void shouldChangeNIPWhenConfirmIsCorrect() {
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class);
		CreditCardService cardService = new CreditCardService(mockCreditCardRepository);
		
		when(mockCreditCardRepository.updateNip(Mockito.anyString()))
						.thenReturn(expectedCreditCardNIPChange);
		
		CreditCard changedNIP = cardService.updateNip(fourDigitNip, fourDigitNip);
		assertEquals(changedNIP, expectedCreditCardNIPChange);
		
	}

	@Test(expected = NipUpdateException.class)
	public void shouldNotChangeNIPWhenConfirmIsIncorrect() { 
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class);
		CreditCardService cardService = new
		CreditCardService(mockCreditCardRepository);
		  
		when(mockCreditCardRepository.updateNip(Mockito.anyString()))
				.thenReturn(expectedCreditCardNIPChange);
		  
	    cardService.updateNip(fourDigitNip, fourDigitNip.concat("1"));
    }
	
	@Test(expected = NipUpdateMissingCharactersException.class)
	public void shouldNotChangeNIPWhenLessThanFourDigitsGiven() { 
		String threeDigitNIP = "123";
		CreditCardRepository mockCreditCardRepository = mock(CreditCardRepository.class); 
		  CreditCardService cardService = new CreditCardService(mockCreditCardRepository);
		  
		  when(mockCreditCardRepository.updateNip(Mockito.anyString()))
		  .thenReturn(expectedCreditCardNIPChange);
		  
		  cardService.updateNip(threeDigitNIP, fourDigitNip);
	  }
	 
}
