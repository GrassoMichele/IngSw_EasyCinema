package easycinema.fabrication;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import easycinema.dominio.Cliente;
import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Titolare;

class GestoreUtentiTest {
	private GestoreUtenti gestoreUtenti;

	@BeforeEach
	void setUp() {
		gestoreUtenti = GestoreUtenti.getIstanza();	
	}
	
	@AfterEach
	void tearDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//uso la reflection per resettare l'oggetto singleton in modo da evitare che i test si influenzino tra loro.
		Field istanza = GestoreUtenti.class.getDeclaredField("istanza");
		istanza.setAccessible(true);
		istanza.set(null,null);		
	}
	
	@Nested
	class NuovoCliente {		
		@Test
		void testInserimentoValido() {
			int num_utenti = gestoreUtenti.getNumeroUtenti();
			int expected = num_utenti + 1;
			
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Mario", "Rossi", "Corso Italia", false, 'F', 2000));
			
			assertEquals(expected, gestoreUtenti.getNumeroUtenti());
		}
		
		@Test
		void testDueInserimentiValidi() {
			int num_utenti = gestoreUtenti.getNumeroUtenti();
			int expected = num_utenti + 2;
			
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF1", "Mario", "Rossi", "Corso Italia", false, 'F', 2000));
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF2", "Eugenio", "Bianchi", "Via delle Ginestre", true, 'F', 2000));
		
			assertEquals(expected, gestoreUtenti.getNumeroUtenti());
		}
		
		@Test
		void testCFDoppione() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Mario", "Rossi", "Corso Italia", true, 'F', 2000));
			int num_utenti = gestoreUtenti.getNumeroUtenti();
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestoreUtenti.nuovoCliente("CF", "Giuseppe", "Verdi", "Viale Libertà", true, 'M', 2000));
		    assertEquals("Il codice fiscale inserito appartiene già ad un utente!", exception.getMessage());
			assertEquals(num_utenti, gestoreUtenti.getNumeroUtenti());
		}		
	}
	
	@Nested
	class AutenticaUtente {			
		@BeforeEach
		void setUp() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", false, 'F', 1950));	
		}
		
		@Test
		void testCredenzialiErrate() {
			assertEquals("Fallita", gestoreUtenti.autenticaUtente("prova", "prova"));
		}	
		
		@Test
		void testCredenzialiTitolare() {
			assertEquals("titolare", gestoreUtenti.autenticaUtente("admin", "admin"));
		}
		
		@Test
		void testCredenzialiCliente() {
			assertEquals("cliente", gestoreUtenti.autenticaUtente("CF", "CF"));			
		}
	}
	
	@Nested
	class ControlloAutorizzazione {			
	
		@Test
		void testClienteRichiedePrivilegiTitolare() {
			//occorre settare l'utente corrente
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", false, 'M', 1996));	
			gestoreUtenti.autenticaUtente("CF", "CF");
			assertFalse(gestoreUtenti.controlloAutorizzazione(Titolare.class));
		}	
		
		@Test
		void testTitolareRichiedePrivilegiCliente() {
			gestoreUtenti.autenticaUtente("admin", "admin");
			assertFalse(gestoreUtenti.controlloAutorizzazione(Cliente.class));
		}
		
		@Test
		void testClienteRichiedePrivilegiCliente() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", true, 'F', 1960));	
			gestoreUtenti.autenticaUtente("CF", "CF");
			assertTrue(gestoreUtenti.controlloAutorizzazione(Cliente.class));
		}
		
		@Test
		void testTitolareRichiedePrivilegiTitolare() {
			gestoreUtenti.autenticaUtente("admin", "admin");
			assertTrue(gestoreUtenti.controlloAutorizzazione(Titolare.class));
		}
		
		@Test
		void testUtenteNonAutenticatoRichiedePrivilegiTitolare() {
			assertFalse(gestoreUtenti.controlloAutorizzazione(Titolare.class));
		}
		
		@Test
		void testUtenteNonAutenticatoRichiedePrivilegiCliente() {
			assertFalse(gestoreUtenti.controlloAutorizzazione(Cliente.class));
		}
	}
	
	@Nested
	class ModificaCreditoCliente {			
		@BeforeEach
		void setUp() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", false, 'F', 1950));	
		}
		
		@Test
		void testClienteInesistente() {
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestoreUtenti.modificaCreditoCliente("prova", 20));
		    assertEquals("ERRORE: Il codice fiscale non corrisponde ad alcun cliente.", exception.getMessage());
		}	
		
		@Test
		void testImportoNullo() {
			double importo = 0;
			assertDoesNotThrow(() -> gestoreUtenti.modificaCreditoCliente("CF", importo));
			
			gestoreUtenti.autenticaUtente("CF", "CF");
			Cliente c = gestoreUtenti.getClienteCorrente();
			assertEquals(10, c.getCredito());
		}
		
		@Test
		void testImportoNegativo() {
			double importo = -10;
			assertDoesNotThrow(() -> gestoreUtenti.modificaCreditoCliente("CF", importo));
			
			gestoreUtenti.autenticaUtente("CF", "CF");
			Cliente c = gestoreUtenti.getClienteCorrente();
			assertEquals(10 + importo, c.getCredito());		
		}
		
		@Test
		void testImportoPositivo() {
			double importo = 10;
			assertDoesNotThrow(() -> gestoreUtenti.modificaCreditoCliente("CF", importo));
			
			gestoreUtenti.autenticaUtente("CF", "CF");
			Cliente c = gestoreUtenti.getClienteCorrente();
			assertEquals(10 + importo, c.getCredito());				
		}
	}

}
