package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import easycinema.EccezioneDominio;
import easycinema.GestoreUtenti;


@ExtendWith(MockitoExtension.class)
class EasyCinemaTest {
	private EasyCinema ec;
	
	@AfterEach
	void tearDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//uso la reflection per resettare l'oggetto singleton in modo da evitare che i test si influenzino tra loro.
		Field istanza = EasyCinema.class.getDeclaredField("istanza");
		istanza.setAccessible(true);
		istanza.set(null,null);		
	}
	
	// -- test: Controllo tecnologia sala
	@Nested
	class ControlloTecnologiaSala {
		@Mock private Sala s;
		private boolean proiezione3D;		
		
		@BeforeEach
		void setUp() {
			ec = EasyCinema.getIstanza();
		}
		
		@Test
		void testControlloTecnologiaSala_Proiezione3DSalaAbilitata() {
			proiezione3D = true;
			when(s.is_3D()).thenReturn(true);
			assertTrue(ec.controlloTecnologiaSala(s, proiezione3D));
		}
		
		@Test
		void testControlloTecnologiaSala_Proiezione3DSalaNonAbilitata() {
			proiezione3D = true;
			when(s.is_3D()).thenReturn(false);
			assertFalse(ec.controlloTecnologiaSala(s, proiezione3D));
		}
		
		@Test
		void testControlloTecnologiaSala_Proiezione2DSalaAbilitata() {
			proiezione3D = false;
			when(s.is_2D()).thenReturn(true);
			assertTrue(ec.controlloTecnologiaSala(s, proiezione3D));
		}
		
		@Test
		void testControlloTecnologiaSala_Proiezione2DSalaNonAbilitata() {
			proiezione3D = false;
			when(s.is_2D()).thenReturn(false);
			assertFalse(ec.controlloTecnologiaSala(s, proiezione3D));
		}
	}
		
	
	// -- test: Nuova prenotazione
	@Nested
	class NuovaPrenotazione {
		@Mock private GestoreUtenti gestoreUtenti;
		@Mock private Cliente c;
		@Mock private Proiezione pr;	
		
		@Test
		void testNuovaPrenotazioneProiezioneValida() {	
			try (MockedStatic<GestoreUtenti> mockedGestoreU_staticMethod = mockStatic(GestoreUtenti.class)) {
				mockedGestoreU_staticMethod.when(() -> GestoreUtenti.getIstanza()).thenReturn(gestoreUtenti);
				try (MockedConstruction<Catalogo> mocked = mockConstruction(Catalogo.class,
						(mock, context) -> {
							when(mock.getProiezione("codiceProiezione")).thenReturn(pr);
						})) {
					try (MockedStatic<Catalogo> mockedCa_staticMethod = mockStatic(Catalogo.class)) {
						mockedCa_staticMethod.when(() -> Catalogo.controlloValiditaTemporaleProiezione(any(),any(),anyInt()))
						.thenReturn(true);

						when(gestoreUtenti.getClienteCorrente()).thenReturn(c);

						ec = EasyCinema.getIstanza();
						assertDoesNotThrow(() -> ec.nuovaPrenotazione("codiceProiezione"));	
					}
				}
			}
			
		}
		
		@Test
		void testNuovaPrenotazioneProiezioneNonValida_Codice() {		
			try (MockedConstruction<Catalogo> mocked = mockConstruction(Catalogo.class,
					(mock, context) -> {
						when(mock.getProiezione(anyString())).thenReturn(null);
					})) {
				ec = EasyCinema.getIstanza();
				Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.nuovaPrenotazione("codiceProiezione"));
			    assertEquals("Il codice della proiezione non è valido.", exception.getMessage());
			}
		}
		
		@Test
		void testNuovaPrenotazioneProiezioneNonValida_DataOra() {		
			try (MockedConstruction<Catalogo> mocked = mockConstruction(Catalogo.class,
					(mock, context) -> {
						when(mock.getProiezione(anyString())).thenReturn(pr);
					})) {
				try (MockedStatic<Catalogo> mockedCa_staticMethod = mockStatic(Catalogo.class)) {
					mockedCa_staticMethod.when(() -> Catalogo.controlloValiditaTemporaleProiezione(any(),any(),anyInt()))
						.thenReturn(false);

					ec = EasyCinema.getIstanza();
					Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.nuovaPrenotazione("codiceProiezione"));
				    assertEquals("Non è più possibile effettuare una prenotazione per la proiezione richiesta", exception.getMessage());
				}
			}
		}
	}
	

	// -- test: Conferma prenotazione
	@Nested
	class ConfermaPrenotazione {
		@Mock private GestoreUtenti gestoreUtenti;
		@Mock private Cliente c;
		@Mock private Proiezione pr;	
		private int numPrenotazioni;
		
		@ParameterizedTest
		@ValueSource(doubles = { 5.0, 10.0, 15.0 })
		void testConfermaPrenotazione_PrenotazioneInCorso(double totalePrenotazione) {
			try (MockedStatic<GestoreUtenti> mockedGestoreU_staticMethod = mockStatic(GestoreUtenti.class)) {
				mockedGestoreU_staticMethod.when(() -> GestoreUtenti.getIstanza()).thenReturn(gestoreUtenti);
				// il credito del cliente è pari a 10.
				try (MockedConstruction<Prenotazione> mockedPr = mockConstruction(Prenotazione.class,
						(mock, context) -> {
							// Il totale della prenotazione è 5
							when(mock.getTotale()).thenReturn(totalePrenotazione);
						})) {
					try (MockedConstruction<Catalogo> mockedCa_constructor = mockConstruction(Catalogo.class,
							(mock, context) -> {
								when(mock.getProiezione(anyString())).thenReturn(pr);
							})) {

						try (MockedStatic<Catalogo> mockedCa_staticMethod = mockStatic(Catalogo.class)) {
							mockedCa_staticMethod.when(() -> Catalogo.controlloValiditaTemporaleProiezione(any(),any(),anyInt()))
							.thenReturn(true);

							when(gestoreUtenti.getClienteCorrente()).thenReturn(c);

							ec = EasyCinema.getIstanza();
							numPrenotazioni = ec.getPrenotazioni().size();

							if (totalePrenotazione == 15.0) {
								try {
									doThrow(new EccezioneDominio("")).when(gestoreUtenti).modificaCreditoCliente(-15.0);
									ec.nuovaPrenotazione("codiceProiezione");	
									ec.confermaPrenotazione();

								}
								catch(EccezioneDominio e) {
									assertEquals(numPrenotazioni, ec.getPrenotazioni().size());
								}			
							}
							else {							
								assertDoesNotThrow(() -> ec.nuovaPrenotazione("codiceProiezione"));	
								assertDoesNotThrow(() -> ec.confermaPrenotazione());
								assertEquals(numPrenotazioni+1, ec.getPrenotazioni().size());							
							}		    
						}
					}
				}				
			}			
		}
		
		@Test
		void testConfermaPrenotazione_NessunaPrenotazioneInCorso() {
			ec = EasyCinema.getIstanza();
			Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.confermaPrenotazione());
		    assertEquals("Nessuna prenotazione in corso!", exception.getMessage());
		}
	} 

	@Nested
	class DisdiciPrenotazione {
		@Mock Cliente clienteCorrente;
		@Mock Cliente altroCliente;
		@Mock Proiezione proiezione;
		@Mock Prenotazione prenotazione;
		@Mock GestoreUtenti gestoreUtentiTest;
		
		List<Prenotazione> prenotazioniTest = new ArrayList<Prenotazione>(); 
		int numPrenotazioni;
		
		@BeforeEach
		void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			ec = EasyCinema.getIstanza();
			
			when(prenotazione.getCodice()).thenReturn("abcd");
			prenotazioniTest.add(prenotazione);
			numPrenotazioni = prenotazioniTest.size();
						
			Field prenotazioni = EasyCinema.class.getDeclaredField("prenotazioni");
			prenotazioni.setAccessible(true);
			prenotazioni.set(ec, prenotazioniTest);	

			Field gestoreUtenti = EasyCinema.class.getDeclaredField("gestoreUtenti");
			gestoreUtenti.setAccessible(true);
			gestoreUtenti.set(ec, gestoreUtentiTest);	
		}
		
		@Test
		void testPrenotazioneInesistente() {
			Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.disdiciPrenotazione("pr123"));
		    assertEquals("Il codice inserito non corrisponde ad alcuna prenotazione.", exception.getMessage());
		    assertEquals(numPrenotazioni, ec.getPrenotazioni().size());
		}
		
		@Test
		void testPrenotazioneAltroUtente() {
			when(gestoreUtentiTest.getClienteCorrente()).thenReturn(clienteCorrente);	
			when(prenotazione.getCliente()).thenReturn(altroCliente);			

			Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.disdiciPrenotazione("abcd"));
		    assertEquals("La prenotazione può essere annullata solamente dal cliente che l'ha effettuata!", exception.getMessage());
		    assertEquals(numPrenotazioni, ec.getPrenotazioni().size());
		}
		
		@Test
		void testMargineTemporaleNonRispettato() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			when(gestoreUtentiTest.getClienteCorrente()).thenReturn(clienteCorrente);	
			when(prenotazione.getCliente()).thenReturn(clienteCorrente);
			when(prenotazione.getProiezione()).thenReturn(proiezione);
						
			try (MockedStatic<Catalogo> mockedCatalogo_staticMethod = mockStatic(Catalogo.class)) {
				mockedCatalogo_staticMethod.when(() -> Catalogo.controlloValiditaTemporaleProiezione(proiezione.getData(),proiezione.getOra(),-120)).thenReturn(false);
			
				Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.disdiciPrenotazione("abcd"));
			    assertEquals("Non è più possibile disdire la prenotazione richiesta (sono richieste 2 ore di preavviso).", exception.getMessage());
				assertEquals(numPrenotazioni, ec.getPrenotazioni().size());
			}			
		}
		
		@Test
		void testAnnullamentoValido() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			when(gestoreUtentiTest.getClienteCorrente()).thenReturn(clienteCorrente);	
			when(prenotazione.getCliente()).thenReturn(clienteCorrente);
			when(prenotazione.getProiezione()).thenReturn(proiezione);
			
			try (MockedStatic<Catalogo> mockedCatalogo_staticMethod = mockStatic(Catalogo.class)) {
				mockedCatalogo_staticMethod.when(() -> Catalogo.controlloValiditaTemporaleProiezione(proiezione.getData(),proiezione.getOra(),-120)).thenReturn(true);
			
				assertDoesNotThrow(() -> ec.disdiciPrenotazione("abcd"));
			    assertEquals(numPrenotazioni-1, ec.getPrenotazioni().size());
			}			
		}
	}
	
}
