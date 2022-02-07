package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;


@ExtendWith(MockitoExtension.class)
class ProiezioneTest {
	
	@Mock private Film f;
	@Mock private Sala s;
	
	private Proiezione pr;


	//test del metodo privato setTariffa (invocato dal il costruttore) senza l'invocazione interna di calcolaTariffa
	@Test
	void testSetTariffaPositiva_NoProiezione3D_NoTopFilm() {
		boolean _3D = false;				
		when(f.isTopFilm()).thenReturn(false);
		double tariffaBase = 2.5;
		
		assertDoesNotThrow(() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), _3D, tariffaBase));
		assertEquals(tariffaBase, pr.getTariffa());		
	}
	
	@Test
	void testSetTariffaPositiva_Proiezione3D_NoTopFilm() {
		boolean _3D = true;				
		when(f.isTopFilm()).thenReturn(false);
		double tariffaBase = 3;
		
		// le proiezioni 3D hanno un supplemento del 15% della tariffa
		double tariffa_expected = tariffaBase * 1.15;		 

		assertDoesNotThrow(() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), _3D, tariffaBase));
		assertEquals(tariffa_expected, pr.getTariffa(),0.01);	
	}
	
	@Test
	void testSetTariffaPositiva_NoProiezione3D_TopFilm() {
		boolean _3D = false;				
		when(f.isTopFilm()).thenReturn(true);
		double tariffaBase = 9.6;
		
		// le proiezioni di top film hanno un supplemento del 15% della tariffa
		double tariffa_expected = tariffaBase * 1.15;		 

		assertDoesNotThrow(() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), _3D, tariffaBase));
		assertEquals(tariffa_expected, pr.getTariffa(),0.01);
	}
	
	@Test	
	void testSetTariffaPositiva_Proiezione3D_TopFilm() {
		boolean _3D = true;				
		when(f.isTopFilm()).thenReturn(true);
		double tariffaBase = 10;
		
		double tariffa_expected = tariffaBase * 1.15 * 1.15;		 

		assertDoesNotThrow(() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), _3D, tariffaBase));
		assertEquals(tariffa_expected, pr.getTariffa(),0.01);
	}
	
	@Test 
	void testSetTariffaNulla() {	
		double tariffaBase = 0;
	
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), true, tariffaBase));
		assertEquals("La tariffa deve essere maggiore di 0.", exception.getMessage());
		assertNull(pr);
	}
	
	@Test
	void testSetTariffaNegativa() {
		double tariffaBase = -1;
		
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> pr = new Proiezione("abcd", f, s, LocalDate.now(), LocalTime.now(), true, tariffaBase));
		assertEquals("La tariffa deve essere maggiore di 0.", exception.getMessage());
		assertNull(pr);
	}
	

}
