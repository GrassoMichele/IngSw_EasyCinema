package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import easycinema.EccezioneDominio;
import easycinema.SaleFactory;


class SalaTest {
	private Sala s;

	// -- test: Numero posti totali
	@Test
	void testSetNumPostiTotaliPositivo() {
		int numPostiTotali = 1;
		assertDoesNotThrow(() -> s = SaleFactory.creaSala("Basic", "Sala", 1, 0, true, false));

		assertEquals(numPostiTotali, s.getNumPostiTotali());
	}

	@Test
	void testSetNumPostiTotaliNullo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = 0;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = SaleFactory.creaSala("Luxe", "Sala", numPoltrone, numPostazioniDisabili, true, false));
		assertEquals("Deve essere presente almeno un posto in sala!", exception.getMessage());
		assertNull(s);
	}

	@Test
	void testSetNumPostiTotaliNegativo() {
		int numPoltrone = -1;
		int numPostazioniDisabili = -1;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = SaleFactory.creaSala("Basic", "Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Non è possibile avere un numero negativo di poltrone o postazioni disabili.", exception.getMessage());
		assertNull(s);
	}
	
	@Test
	void testSetNumPoltroneNegativo() {
		int numPoltrone = -1;
		int numPostazioniDisabili = 3;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = SaleFactory.creaSala("Luxe", "Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Non è possibile avere un numero negativo di poltrone o postazioni disabili.", exception.getMessage());
		assertNull(s);
	}
	
	@Test
	void testSetNumPostazioniDisabiliNegativo() {
		int numPoltrone = 10;
		int numPostazioniDisabili = -1;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = SaleFactory.creaSala("Basic", "Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Non è possibile avere un numero negativo di poltrone o postazioni disabili.", exception.getMessage());
		assertNull(s);
	}

	
	// -- test: Numero di istanze di PostoSala create
	@Test
	void testCreaPostiSalaNumPostiTotaliPositivo() {
		int numPostiTotali = 2;
		int numPoltrone = 1;
		int numPostazioniDisabili = 1;

		assertDoesNotThrow(() -> s = SaleFactory.creaSala("Basic", "La sala", numPoltrone, numPostazioniDisabili, true, true));
		assertEquals(numPostiTotali, s.getSizePostiSala());
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNullo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = 0;

		assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala("Basic", "La sala", numPoltrone, numPostazioniDisabili, true, false));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNegativo() {
		int numPoltrone = 0;
		int numPostazioniDisabili = -1;

		assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala("Luxe", "La sala", numPoltrone, numPostazioniDisabili, false, true));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

}
