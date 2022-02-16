package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import easycinema.EccezioneDominio;
import easycinema.GestoreUtenti;

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
			
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Mario", "Rossi", "Corso Italia", false));
			
			assertEquals(expected, gestoreUtenti.getNumeroUtenti());
		}
		
		@Test
		void testDueInserimentiValidi() {
			int num_utenti = gestoreUtenti.getNumeroUtenti();
			int expected = num_utenti + 2;
			
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF1", "Mario", "Rossi", "Corso Italia", false));
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF2", "Eugenio", "Bianchi", "Via delle Ginestre", true));
		
			assertEquals(expected, gestoreUtenti.getNumeroUtenti());
		}
		
		@Test
		void testCFDoppione() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Mario", "Rossi", "Corso Italia", true));
			int num_utenti = gestoreUtenti.getNumeroUtenti();
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestoreUtenti.nuovoCliente("CF", "Giuseppe", "Verdi", "Viale Libertà", true));
		    assertEquals("Il codice fiscale inserito appartiene già ad un utente!", exception.getMessage());
			assertEquals(num_utenti, gestoreUtenti.getNumeroUtenti());
		}		
	}
	
	@Nested
	class AutenticaUtente {			
		@BeforeEach
		void setUp() {
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", false));	
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
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", false));	
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
			assertDoesNotThrow(() -> gestoreUtenti.nuovoCliente("CF", "Nome", "Cognome", "Via", true));	
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

}
