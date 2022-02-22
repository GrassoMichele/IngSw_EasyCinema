package easycinema.fabrication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Sala;

class SaleFactoryTest {
	private Sala s;

	@Test
	void testSetNumPoltroneNegativo() {
		int numPoltrone = -1;
		int numPostazioniDisabili = 3;
		Throwable exception = assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala("Luxe", "Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Non è possibile avere un numero negativo di poltrone o postazioni disabili.", exception.getMessage());
		assertNull(s);
	}
	
	@Test
	void testSetNumPostazioniDisabiliNegativo() {
		int numPoltrone = 10;
		int numPostazioniDisabili = -1;
		Throwable exception = assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala("Basic", "Sala", numPoltrone, numPostazioniDisabili, false, true));
		assertEquals("Non è possibile avere un numero negativo di poltrone o postazioni disabili.", exception.getMessage());
		assertNull(s);
	}
	
	@Test
	void testTecnologiaNo2DNo3D() {
		boolean _2D = false;
		boolean _3D = false;
		Throwable exception = assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala("Basic", "Sala", 10, 10, _2D, _3D));
		assertEquals("ERRORE: La sala deve supportare almeno una tra le due tecnologie: 2D, 3D.", exception.getMessage());
		assertNull(s);
	}
	
	@Test
	void testTipologiaSalaErrata() {
		String tipologiaSala = "Comfort";
		Throwable exception = assertThrows(EccezioneDominio.class, () -> s = SaleFactory.creaSala(tipologiaSala, "Sala", 10, 10, true, true));
		assertEquals("Tipologia di sala non esistente!", exception.getMessage());
		assertNull(s);
	}

}
