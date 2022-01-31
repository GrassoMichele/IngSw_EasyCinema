package easycinema.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class PrenotazioneTest {
	private Prenotazione p;
	private List<Integer> postiOccupatiPrenotazione;
	@Mock Cliente c;
	@Mock Proiezione prAttuale, prInteressata;
	
	@BeforeEach 
	void setUp() {
		p = new Prenotazione(c,prAttuale);
		p.aggiungiBiglietto(new PostoSala(2));
		postiOccupatiPrenotazione = new ArrayList<>(Arrays.asList(2));
	}
	
	
	@Test
	void testOttieniPostiBiglietti_StessaProiezione1Biglietto() {
		assertEquals(postiOccupatiPrenotazione, p.ottieniPostiBiglietti(prAttuale));
	}
	
	@Test
	void testOttieniPostiBiglietti_StessaProiezione3Biglietti() {
		p.aggiungiBiglietto(new PostoSala(40));
		p.aggiungiBiglietto(new PostoSala(17));
		List<Integer> altriPosti=Arrays.asList(40,17);
		postiOccupatiPrenotazione.addAll(altriPosti);
		assertEquals(postiOccupatiPrenotazione, p.ottieniPostiBiglietti(prAttuale));
	}
	
	@Test
	void testOttieniPostiBiglietti_DiversaProiezione() {
		assertEquals(new ArrayList<Integer>() , p.ottieniPostiBiglietti(prInteressata));
	}

}
