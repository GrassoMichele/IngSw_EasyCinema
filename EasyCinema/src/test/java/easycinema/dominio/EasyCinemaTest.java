package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class EasyCinemaTest {
	private EasyCinema ec;
	
	// -- test: Controllo tecnologia sala
	@Nested
	class ControlloTecnologiaSala {
		@Mock private Sala s;
		private boolean proiezione3D;		
		
		@BeforeEach
		void setUp() {
			ec = new EasyCinema();
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
		@Mock private Proiezione pr;	
		
		@Test
		void testNuovaPrenotazioneProiezioneValida() {	
			try (MockedConstruction<Catalogo> mocked = mockConstruction(Catalogo.class,
					(mock, context) -> {
						when(mock.getProiezione("codiceProiezione")).thenReturn(pr);
					})) {
				ec = new EasyCinema();
				assertDoesNotThrow(() -> ec.nuovaPrenotazione("codiceProiezione"));	
			}
		}
		
		@Test
		void testNuovaPrenotazioneProiezioneNonValida() {		// in caso si può saltare
			try (MockedConstruction<Catalogo> mocked = mockConstruction(Catalogo.class,
					(mock, context) -> {
						when(mock.getProiezione(anyString())).thenReturn(null);
					})) {
				ec = new EasyCinema();
				Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.nuovaPrenotazione("codiceProiezione"));
			    assertEquals("Il codice della proiezione non è valido.", exception.getMessage());
			}
		}
	}
	

	// -- test: Conferma prenotazione
	@Nested
	class ConfermaPrenotazione {
		@Mock private Proiezione pr;		
		private int numPrenotazioni;
		
		@ParameterizedTest
		@ValueSource(doubles = { 5.0, 10.0, 15.0 })
		void testConfermaPrenotazione_PrenotazioneInCorso(double totalePrenotazione) {
			// il credito del cliente è pari a 10.
			try (MockedConstruction<Prenotazione> mockedPr = mockConstruction(Prenotazione.class,
					(mock, context) -> {
						// Il totale della prenotazione è 5
						when(mock.getTotale()).thenReturn(totalePrenotazione);
					})) {
				try (MockedConstruction<Catalogo> mockedCa = mockConstruction(Catalogo.class,
						(mock, context) -> {
							when(mock.getProiezione(anyString())).thenReturn(pr);
						})) {
					ec = new EasyCinema();
					numPrenotazioni = ec.getPrenotazioni().size();
					try {
						ec.nuovaPrenotazione("codiceProiezione");	
						ec.confermaPrenotazione();
						if (totalePrenotazione != 15.0)
							assertEquals(numPrenotazioni+1, ec.getPrenotazioni().size());
						else
							assertEquals(numPrenotazioni, ec.getPrenotazioni().size());
					}
					catch(EccezioneDominio e) {}
				}
			}				
		}
		
		@Test
		void testConfermaPrenotazione_NessunaPrenotazioneInCorso() {
			ec = new EasyCinema();
			Throwable exception = assertThrows(EccezioneDominio.class, () -> ec.confermaPrenotazione());
		    assertEquals("Nessuna prenotazione in corso!", exception.getMessage());
		}
	} 
}
