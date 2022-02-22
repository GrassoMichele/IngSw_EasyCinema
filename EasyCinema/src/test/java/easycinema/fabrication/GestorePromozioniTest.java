package easycinema.fabrication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import easycinema.dominio.Cliente;
import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Prenotazione;
import easycinema.dominio.Proiezione;
import easycinema.dominio.Promozione;


@ExtendWith(MockitoExtension.class)
class GestorePromozioniTest {
	private GestorePromozioni gestorePromozioni;

	@BeforeEach
	void setUp() {
		gestorePromozioni = GestorePromozioni.getIstanza();	
	}
	
	@AfterEach
	void tearDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//uso la reflection per resettare l'oggetto singleton in modo da evitare che i test si influenzino tra loro.
		Field istanza = GestorePromozioni.class.getDeclaredField("istanza");
		istanza.setAccessible(true);
		istanza.set(null,null);		
	}
	
	@Nested
	class creaPromozione {	

		@Test
		void testPercentualeScontoInferiore1() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 0;

			List<String> condizione = new ArrayList<String>();
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Disabile", condizione, scontoPercentuale));
			assertEquals("La percentuale di sconto deve essere compresa tra 1 e 100", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPercentualeScontoSuperiore100() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 101;

			List<String> condizione = new ArrayList<String>();
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Disabile", condizione, scontoPercentuale));
			assertEquals("La percentuale di sconto deve essere compresa tra 1 e 100", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoDisabileValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 10;

			List<String> condizione = new ArrayList<String>();
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Disabile", condizione, scontoPercentuale));
			assertEquals(numPromozioni+1, gestorePromozioni.getPromozioni().size());
		}
		
		
		@Test
		void testPromoGiornoGiornoNonValido() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 5;

			List<String> condizione = new ArrayList<String>();
			condizione.add("39/04/2022");
			condizione.add("N");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Giorno", condizione, scontoPercentuale));
			assertEquals("Formato del giorno non corretto.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoGiornoSessoNonValido() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 5;

			List<String> condizione = new ArrayList<String>();
			condizione.add("19/04/2022");
			condizione.add("");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Giorno", condizione, scontoPercentuale));
			assertEquals("Sesso non valido.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoGiornoValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 5;

			List<String> condizione = new ArrayList<String>();
			condizione.add("19/04/2022");
			condizione.add("M");
			
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Giorno", condizione, scontoPercentuale));
			assertEquals(numPromozioni + 1, gestorePromozioni.getPromozioni().size());
		}
		
		
		@Test
		void testPromoEt‡Et‡MinimaNonValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 15;

			List<String> condizione = new ArrayList<String>();
			condizione.add("-10");
			condizione.add("10");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Et‡", condizione, scontoPercentuale));
			assertEquals("Et‡ minima e/o massima non valida.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoEt‡Et‡MassimaNonValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 15;

			List<String> condizione = new ArrayList<String>();
			condizione.add("10");
			condizione.add("1");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("Et‡", condizione, scontoPercentuale));
			assertEquals("Et‡ minima e/o massima non valida.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoEt‡Valida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 15;

			List<String> condizione = new ArrayList<String>();
			condizione.add("10");
			condizione.add("20");
			
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Et‡", condizione, scontoPercentuale));
			assertEquals(numPromozioni + 1, gestorePromozioni.getPromozioni().size());
		}
		
		
		@Test
		void testPromoFasciaOrariaInizioNonValido() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 8;

			List<String> condizione = new ArrayList<String>();
			condizione.add("errore");
			condizione.add("10:00");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("FasciaOraria", condizione, scontoPercentuale));
			assertEquals("Formato inizio e/o fine fascia oraria non corretto.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoFasciaOrariaFineNonValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 8;

			List<String> condizione = new ArrayList<String>();
			condizione.add("18:30");
			condizione.add("errore");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("FasciaOraria", condizione, scontoPercentuale));
			assertEquals("Formato inizio e/o fine fascia oraria non corretto.", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoFasciaOrariaValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 8;

			List<String> condizione = new ArrayList<String>();
			condizione.add("8:00");
			condizione.add("10:00");
			
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("FasciaOraria", condizione, scontoPercentuale));
			assertEquals(numPromozioni + 1, gestorePromozioni.getPromozioni().size());
		}
		
		@Test
		void testPromoNonValida() {
			int numPromozioni = gestorePromozioni.getPromozioni().size();
			int scontoPercentuale = 22;

			List<String> condizione = new ArrayList<String>();
			condizione.add("8:00");
			condizione.add("10:00");
			
			Throwable exception = assertThrows(EccezioneDominio.class, () -> gestorePromozioni.creaPromozione("NumeroBiglietti", condizione, scontoPercentuale));
			assertEquals("Tipologia di promozione non esistente!", exception.getMessage());
			assertEquals(numPromozioni, gestorePromozioni.getPromozioni().size());
		}
			
	}
	
	
	@Nested
	class associaPromozione {
		@Mock Prenotazione prenotazione;
		@Mock Cliente c;
		@Mock Proiezione proiezione;
		int scontoPercentuale = 10;
		List<String> condizione = new ArrayList<String>();
		
		@Test
		void testPromoDisabile() {					
			when(prenotazione.getCliente()).thenReturn(c);
			when(c.getDisabile()).thenReturn(true);			
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Disabile", condizione, scontoPercentuale));
			
			Promozione promozione = gestorePromozioni.associaPromozione(prenotazione);
			assertEquals(1, promozione.getPromozioni().size());			
		}
		
		@Test
		void testPromoEt‡() {
			when(prenotazione.getCliente()).thenReturn(c);
			when(c.getAnnoNascita()).thenReturn(2010);			
			condizione.add("10");
			condizione.add("20");
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Et‡", condizione, scontoPercentuale));
			
			Promozione promozione = gestorePromozioni.associaPromozione(prenotazione);
			assertEquals(1, promozione.getPromozioni().size());			
		}
		
		@Test
		void testPromoGiorno() {
			String giorno = "20/05/2022";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
			LocalDate data = LocalDate.parse(giorno, formatter);
			when(prenotazione.getCliente()).thenReturn(c);
			when(prenotazione.getProiezione()).thenReturn(proiezione);
			when(c.getSesso()).thenReturn('M');		
			when(proiezione.getData()).thenReturn(data);
			condizione.add(giorno);
			condizione.add("M");
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Giorno", condizione, scontoPercentuale));
			
			Promozione promozione = gestorePromozioni.associaPromozione(prenotazione);
			assertEquals(1, promozione.getPromozioni().size());			
		}

		@Test
		void testPromoFasciaOraria() {
			String inizioFasciaOraria = "08:00";
			String fineFasciaOraria = "10:00";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
			LocalTime oraProiezione = LocalTime.parse("09:00", formatter);
			when(prenotazione.getProiezione()).thenReturn(proiezione);
			when(proiezione.getOra()).thenReturn(oraProiezione);
			condizione.add(inizioFasciaOraria);
			condizione.add(fineFasciaOraria);
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("FasciaOraria", condizione, scontoPercentuale));
			
			Promozione promozione = gestorePromozioni.associaPromozione(prenotazione);
			assertEquals(1, promozione.getPromozioni().size());			
		}
		
		@Test
		void testPromoComposta() {
			when(prenotazione.getCliente()).thenReturn(c);
			when(c.getDisabile()).thenReturn(true);			
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Disabile", condizione, scontoPercentuale));
			
			when(c.getAnnoNascita()).thenReturn(2010);			
			condizione.clear();
			condizione.add("10");
			condizione.add("20");
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Et‡", condizione, scontoPercentuale));
			
			String giorno = "20/05/2022";
			DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
			LocalDate data = LocalDate.parse(giorno, dataFormatter);
			when(prenotazione.getProiezione()).thenReturn(proiezione);
			when(c.getSesso()).thenReturn('M');		
			when(proiezione.getData()).thenReturn(data);
			condizione.clear();
			condizione.add(giorno);
			condizione.add("M");
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("Giorno", condizione, scontoPercentuale));
						
			String inizioFasciaOraria = "08:00";
			String fineFasciaOraria = "10:00";
			DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
			LocalTime oraProiezione = LocalTime.parse("09:00", oraFormatter);
			when(proiezione.getOra()).thenReturn(oraProiezione);
			condizione.clear();
			condizione.add(inizioFasciaOraria);
			condizione.add(fineFasciaOraria);
			assertDoesNotThrow(() -> gestorePromozioni.creaPromozione("FasciaOraria", condizione, scontoPercentuale));
			
			Promozione promozione = gestorePromozioni.associaPromozione(prenotazione);
			assertEquals(4, promozione.getPromozioni().size());			
		}
	
	}
		

}
