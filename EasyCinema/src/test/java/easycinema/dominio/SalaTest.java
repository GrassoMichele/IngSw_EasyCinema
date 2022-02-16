package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import easycinema.EccezioneDominio;


class SalaTest {
	private Sala s;

	// -- test: Numero posti totali
	@Test
	void testSetNumPostiTotaliPositivo() {
		int numPostiTotali = 1;
		assertDoesNotThrow(() -> s = new SalaBasic ("Sala", 1, 0, true, false));

		assertEquals(numPostiTotali, s.getNumPostiTotali());
	}

	@Test
	void testSetNumPostiTotaliNullo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = 0;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = new SalaLuxe("Sala", numPoltrone, numPostazioniDisabili, true, false));
		assertEquals("Deve essere presente almeno un posto in sala!", exception.getMessage());
		assertNull(s);
	}

	@Test
	void testSetNumPostiTotaliNegativo() {
		int numPoltrone = 1;
		int numPostazioniDisabili = -2;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = new SalaBasic("Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Deve essere presente almeno un posto in sala!", exception.getMessage());
		assertNull(s);
	}
	
		
	// -- test: Numero di istanze di PostoSala create
	@Test
	void testCreaPostiSalaNumPostiTotaliPositivo() {
		int numPostiTotali = 2;
		int numPoltrone = 1;
		int numPostazioniDisabili = 1;

		assertDoesNotThrow(() -> s = new SalaBasic("La sala", numPoltrone, numPostazioniDisabili, true, true));
		assertEquals(numPostiTotali, s.getSizePostiSala());
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNullo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = 0;

		assertThrows(EccezioneDominio.class, () -> s = new SalaBasic("La sala", numPoltrone, numPostazioniDisabili, true, false));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNegativo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = -1;

		assertThrows(EccezioneDominio.class, () -> s = new SalaLuxe("La sala", numPoltrone, numPostazioniDisabili, false, true));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

}
