package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Catalogo {
	private Map<String, Proiezione> proiezioni;
	private Map<String, Film> film;
	
	
	public Catalogo() {
		proiezioni = new HashMap<String, Proiezione>();
		film = new HashMap<String, Film>();
		
		caricaFilm();
	}
	
	public Proiezione getProiezione(String codice) {
		return proiezioni.get(codice);
	}
	
	public Proiezione nuovaProiezione(String codice, String codiceFilm, Sala s, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) {
		Film f = film.get(codiceFilm);
		Proiezione pr = new Proiezione(codice, f, s, data, ora, _3D, tariffaBase);
		proiezioni.put(codice, pr);
		return pr;
	}
	
	private void caricaFilm() {
		Film f1 = new Film("12345", "Le Ali della libertà", "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, ...", 135, 1994, "Ambientato nel Maine "
				+ "del 1947...", "Drammatico", true);
		Film f2 = new Film("54321", "Batman Begins", "Christopher Nolan", "Christian Bale, Michael Caine, Morgan Freeman, ...", 140, 2005, " Il protagonista "
				+ "della vicenda è Bruce Wayne...", "Azione", false);
		Film f3 = new Film("19283", "Il Signore degli Anelli: La Compagnia dell'Anello", "Peter Jackson", "Elijah Wood, Sean Astin, Billy Boyd, ...", 178, 2002,
				"La storia ha inizio quando l'Unico Anello, forgiato da Sauron ...", "Avventura", false);
		
		film.put(f1.getCodice(), f1);
		film.put(f2.getCodice(), f2);
		film.put(f3.getCodice(), f3);
	}
	
}
