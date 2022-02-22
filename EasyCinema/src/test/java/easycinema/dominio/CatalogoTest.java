package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CatalogoTest {
	
	@Mock private Proiezione pr;

	
	// -- test: Controllo validità temporale proiezione
	@Test
	void testControlloValiditaTemporaleProiezione_Valida_MinutoSuccessivo() {
		LocalDateTime oneMinuteAfterNow = LocalDateTime.now().plusMinutes(1);
		LocalDate data = oneMinuteAfterNow.toLocalDate();
		LocalTime ora = oneMinuteAfterNow.toLocalTime();
		int margine = 0;
		assertTrue(Catalogo.controlloValiditaTemporaleProiezione(data,ora,margine));
	}
	
	@Test
	void testControlloValiditaTemporaleProiezione_NonValida_MinutoCorrente() {
		LocalDateTime now = LocalDateTime.now();
		LocalDate data = now.toLocalDate();
		LocalTime ora = now.toLocalTime();
		int margine = 0;
		assertFalse(Catalogo.controlloValiditaTemporaleProiezione(data,ora,margine));
	}
	
	@Test
	void testControlloValiditaTemporaleProiezione_NonValida_MinutoPrecedente() {
		LocalDateTime oneMinuteBeforeNow = LocalDateTime.now().minusMinutes(1);
		LocalDate data = oneMinuteBeforeNow.toLocalDate();
		LocalTime ora = oneMinuteBeforeNow.toLocalTime();
		int margine = 0;
		assertFalse(Catalogo.controlloValiditaTemporaleProiezione(data,ora,margine));
	}
	
	
	// -- test: Controllo sovrapposizione proiezione
	@Test
	void testControlloSovrapposizioneProiezione_InteramenteContenuta() {
		// La nuova potenziale proiezione è contenuta temporalmente da una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("12/02/2022",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("13:00",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(180);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("12/02/2022",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("14:00",oraFormatter);
		int durataFilm = 90;						
		
		assertTrue(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_ParzialmenteContenuta() {
		// La nuova potenziale proiezione è contenuta parzialmente da una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("12/02/2022",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("13:00",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(180);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("12/02/2022",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("15:00",oraFormatter);
		int durataFilm = 90;						
		
		assertTrue(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_ContieneInteramente() {
		// La nuova potenziale proiezione contiene temporalmente una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("01/01/2022",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("09:30",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(60);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("01/01/2022",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("09:00",oraFormatter);
		int durataFilm = 120;						
		
		assertTrue(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_ContieneParzialmente() {
		// La nuova potenziale proiezione contiene parzialmente una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("14/02/2022",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("17:30",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(90);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("14/02/2022",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("17:00",oraFormatter);
		int durataFilm = 90;						
		
		assertTrue(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_Coincidenza() {
		// La nuova potenziale proiezione coincide esattamente con una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("05/05/1990",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("20:00",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(100);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("05/05/1990",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("20:00",oraFormatter);
		int durataFilm = 100;						
		
		assertTrue(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_NuovaImmediatamentePrima() {
		// La nuova potenziale proiezione termina immediatamente prima dell'inizio di una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("03/03/1986",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("21:00",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(90);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("03/03/1986",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("19:00",oraFormatter);
		int durataFilm = 105;						
		
		assertFalse(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	@Test
	void testControlloSovrapposizioneProiezione_NuovaImmediatamenteDopo() {
		// La nuova potenziale proiezione inizia immediatamente dopo la fine di una proiezione esistente.
		// Sono da considerare nel calcolo dei tempi anche i 15 minuti di pubblicità iniziale per proiezione.
		
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		LocalDate dataProiezioneEsistente = LocalDate.parse("27/09/2002",dataFormatter);
		LocalTime oraProiezioneEsistente = LocalTime.parse("10:00",oraFormatter);		
		when(pr.getData()).thenReturn(dataProiezioneEsistente);
		when(pr.getOra()).thenReturn(oraProiezioneEsistente);
		when(pr.getDurataFilm()).thenReturn(60);
		
		LocalDate dataProiezioneNuova = LocalDate.parse("27/09/2002",dataFormatter);
		LocalTime oraProiezioneNuova = LocalTime.parse("11:15",oraFormatter);
		int durataFilm = 110;						
		
		assertFalse(Catalogo.controlloSovrapposizioneProiezione(dataProiezioneNuova, oraProiezioneNuova, durataFilm, pr));
	}
	
	
	// -- test: nuovoFilm
	@Test
	void testNuovoFilmCodiceDoppione() {
		Catalogo catalogo = new Catalogo(null);
		assertDoesNotThrow(() -> catalogo.nuovoFilm("Film1", "Film1", null, null, 10, 1960, null, null, false));
		int expected_size = catalogo.getFilm().size();
				
		Throwable exception = assertThrows(EccezioneDominio.class, () -> catalogo.nuovoFilm("Film1", "Film2", null, null, 0, 0, null, null, false));
	    assertEquals("Il codice indicato fa riferimento ad un film già esistente.", exception.getMessage());
	    assertEquals(expected_size, catalogo.getFilm().size());
	}

	
}
