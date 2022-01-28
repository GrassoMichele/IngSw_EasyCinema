package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class SalaTest {
	private Sala s;

	// -- test: Numero posti totali
	@Test
	void testSetNumPostiTotaliPositivo() {
		int numPostiTotali = 1;
		assertDoesNotThrow(() -> s = new Sala("Sala", numPostiTotali, false, false));

		int expected = 1;

		assertEquals(expected, s.getNumPostiTotali());
	}

	@Test
	void testSetNumPostiTotaliNullo() {
		int numPostiTotali = 0;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = new Sala("Sala", numPostiTotali, true, false));
		assertEquals("Deve essere presente almeno un posto in sala!", exception.getMessage());
		assertNull(s);
	}

	@Test
	void testSetNumPostiTotaliNegativo() {
		int numPostiTotali = -1;
		Throwable exception = assertThrows(EccezioneDominio.class,
				() -> s = new Sala("Sala", numPostiTotali, false, true));
		assertEquals("Deve essere presente almeno un posto in sala!", exception.getMessage());
		assertNull(s);
	}

	// -- test: Numero di istanze di PostoSala create
	@Test
	void testCreaPostiSalaNumPostiTotaliPositivo() {
		int numPostiTotali = 1;

		assertDoesNotThrow(() -> s = new Sala("La sala", numPostiTotali, true, true));
		assertEquals(numPostiTotali, s.getSizePostiSala());
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNullo() {
		int numPostiTotali = 0;

		assertThrows(EccezioneDominio.class, () -> s = new Sala("La sala", numPostiTotali, true, false));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

	@Test
	void testCreaPostiSalaNumPostiTotaliNegativo() {
		int numPostiTotali = -1;

		assertThrows(EccezioneDominio.class, () -> s = new Sala("La sala", numPostiTotali, false, true));
		// l'istanza s di Sala non potrà essere creata per via del numero di posti totali non valido.
		// Di conseguenza il riferimento ad s deve essere nullo.
		assertNull(s);
	}

}
