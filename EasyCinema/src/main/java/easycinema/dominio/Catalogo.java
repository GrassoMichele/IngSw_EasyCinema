package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

import easycinema.interfaccia.text.Parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


public class Catalogo {
	private Map<String, Proiezione> proiezioni;
	private Map<String, Film> film;
	
	
	public Catalogo(Map<String, Sala> sale) {
		proiezioni = new HashMap<String, Proiezione>();
		film = new HashMap<String, Film>();
		
		caricaFilm();
		caricaProiezioni(sale);
	}
	
	public void nuovaProiezione(String codice, String codiceFilm, Sala s, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio {
		if (proiezioni.containsKey(codice)) {
			throw new EccezioneDominio("Errore: Esiste già una proiezione con lo stesso codice.");
		}
		boolean valida = controlloValiditaTemporaleProiezione(data, ora, 0);
		if (valida == true) {
			Film f = film.get(codiceFilm);
			if (f != null) {
				int durataFilm = f.getDurata();			
				boolean sovrapposizione = controlloSovrapposizioneProiezioni(s, data, ora, durataFilm);
				if (sovrapposizione == false) {
					Proiezione pr = new Proiezione(codice, f, s, data, ora, _3D, tariffaBase);
					proiezioni.put(codice, pr);
				}
				else {
					throw new EccezioneDominio("Errore: la nuova proiezione si sovrappone con (almeno) un'altra già esistente!");
				}
			}
			else {
				throw new EccezioneDominio("Errore: Non esiste un film con il codice indicato!");
			}
		}
		else {
			throw new EccezioneDominio("Errore: la nuova proiezione fa riferimento ad una data/ora passata.");
		}
	}

	private boolean controlloValiditaTemporaleProiezione(LocalDate data, LocalTime ora, int margine) {
		LocalDateTime data_ora_proiezione = LocalDateTime.of(data,ora);
	    LocalDateTime data_ora_proiezione_con_margine = data_ora_proiezione.plusMinutes(margine);
		return data_ora_proiezione_con_margine.isAfter(LocalDateTime.now());
	}
	
	private boolean controlloSovrapposizioneProiezioni(Sala sala, LocalDate data, LocalTime ora, int durataFilm) {
		for (Proiezione proiezioneEsistente : proiezioni.values()) {
			if (proiezioneEsistente.getSala() == sala) {
				if (controlloSovrapposizioneProiezione(data, ora, durataFilm, proiezioneEsistente) == true)
					return true;
			}			
		}
		return false;
	}
	
	private boolean controlloSovrapposizioneProiezione(LocalDate data, LocalTime ora, int durataFilm, Proiezione proiezioneEsistente) {
		LocalDateTime nuovaProiezioneInizio = LocalDateTime.of(data,ora);
		LocalDateTime nuovaProiezioneFine = nuovaProiezioneInizio.plusMinutes(15 + durataFilm);		// 15 minuti di pubblicità ad inizio proiezione
		
		LocalDateTime proiezioneEsistenteInizio = LocalDateTime.of(proiezioneEsistente.getData(), proiezioneEsistente.getOra());
		LocalDateTime proiezioneEsistenteFine = proiezioneEsistenteInizio.plusMinutes(15 + proiezioneEsistente.getDurataFilm());
		
		// controllo se la nuova proiezione è contenuta temporalmente da una proiezione esistente o viene intersecata da sinistra.
		if (nuovaProiezioneInizio.isAfter(proiezioneEsistenteInizio) && nuovaProiezioneInizio.isBefore(proiezioneEsistenteFine)) {
			return true;
		}
		// controllo se la nuova proiezione contiene temporalmente una proiezione esistente o la interseca da sinistra.
		else if (nuovaProiezioneInizio.isBefore(proiezioneEsistenteInizio) && nuovaProiezioneFine.isAfter(proiezioneEsistenteInizio)) {
			return true;
		}
		else if (nuovaProiezioneInizio.isEqual(proiezioneEsistenteInizio) && nuovaProiezioneFine.isEqual(proiezioneEsistenteFine)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Proiezione getProiezione(String codice) throws EccezioneDominio {		
		Proiezione pr = proiezioni.get(codice);
		if (pr != null) {
			// La prenotazione ad una proiezione è possibile entro i 15 minuti successivi all'inizio della proiezione.
			boolean valida = controlloValiditaTemporaleProiezione(pr.getData(), pr.getOra(), 15); 
			if (valida == false) {
				throw new EccezioneDominio("Non è più possibile effettuare una prenotazione per la proiezione richiesta");
			}
		}		
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

	public Map<String, Film> getFilm() {
		return film;
	}
	
	private void caricaProiezioni(Map<String, Sala> sale) {
		try {
			DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("d/MM/yyyy");
			DateTimeFormatter formatterH = DateTimeFormatter.ofPattern("H:mm");
		
			LocalDate data = LocalDate.parse("17/03/2022", formatterD);		
			LocalTime ora = LocalTime.parse("10:10", formatterH);
			Proiezione pr1 = new Proiezione("pr1", film.get("12345"), sale.get("Indaco"), data, ora, false, 4.00);
			data = LocalDate.parse("07/12/2021", formatterD);		
			ora = LocalTime.parse("01:59", formatterH);
			Proiezione pr2 = new Proiezione("pr2", film.get("54321"), sale.get("Solidago"), data, ora, true, 5.00);
		
			proiezioni.put("pr1", pr1);
			proiezioni.put("pr2", pr2);
		}
		catch(Exception e) {}
		
	}
	
	public Map<String, Proiezione> getProiezioni() {
		return proiezioni;
	}
	
	
}
