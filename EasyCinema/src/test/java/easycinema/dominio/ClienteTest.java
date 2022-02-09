package easycinema.dominio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {
	private Cliente cliente = new Cliente("","","","");

	// -- test: Credito
	@Test
	void testSetCreditoPositivo() {
		double expected = 9.5;
		
		assertDoesNotThrow(() -> cliente.setCredito(9.5));
		assertEquals(expected, cliente.getCredito());
	}

	@Test
	void testSetCreditoNullo() {
		double expected = 0;
		
		assertDoesNotThrow(() -> cliente.setCredito(0));
		assertEquals(expected, cliente.getCredito());
	}
	
	@Test
	void testSetCreditoNegativo() {
		// il credito non deve variare.
		double expected = cliente.getCredito();
		
		Throwable exception = assertThrows(EccezioneDominio.class, () -> cliente.setCredito(-10));
	    assertEquals("Credito insufficiente!", exception.getMessage());
	    assertEquals(expected, cliente.getCredito());
	}
	
}
