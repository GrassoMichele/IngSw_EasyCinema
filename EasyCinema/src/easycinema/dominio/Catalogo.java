package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Catalogo {
	private Map<String, Proiezione> proiezioni;
	private Map<String, Film> film;
	
	
	public Catalogo() {
		proiezioni = new HashMap<String, Proiezione>();
		film = new HashMap<String, Film>();
		
		caricaFilm();
	}
	
	public void nuovaProiezione(String codice, String codiceFilm, Sala s, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio {
		/* da copiare nell'interfaccia
		String date = "20/01/2022";
	    String time = "08:49";
	        
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
	    //convert String to LocalDate
	    LocalDate localDate = LocalDate.parse(date, formatter);
	    
	    formatter = DateTimeFormatter.ofPattern("H:mm");
	    LocalTime localTime = LocalTime.parse(time, formatter);
	 	*/
		
		boolean valida = controlloValidit‡TemporaleProiezione(data, ora, 0);
		if (valida == true) {
			Film f = film.get(codiceFilm);
			int durataFilm = f.getDurata();			
			boolean sovrapposizione = controlloSovrapposizioneProiezioni(s, data, ora, durataFilm);
			if (sovrapposizione == false) {
				Proiezione pr = new Proiezione(codice, f, s, data, ora, _3D, tariffaBase);
				proiezioni.put(codice, pr);
			}
			else {
				throw new EccezioneDominio("Errore: la nuova proiezione si sovrappone con (almeno) un'altra gi‡ esistente!");
			}
		}
		else {
			throw new EccezioneDominio("Errore: la nuova proiezione fa riferimento ad una data/ora passata.");
		}
	}

	private boolean controlloValidit‡TemporaleProiezione(LocalDate data, LocalTime ora, int margine) {
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
		LocalDateTime nuovaProiezioneFine = nuovaProiezioneInizio.plusMinutes(15 + durataFilm);		// 15 minuti di pubblicit‡ ad inizio proiezione
		
		LocalDateTime proiezioneEsistenteInizio = LocalDateTime.of(proiezioneEsistente.getData(), proiezioneEsistente.getOra());
		LocalDateTime proiezioneEsistenteFine = proiezioneEsistenteInizio.plusMinutes(15 + proiezioneEsistente.getDurataFilm());
		
		// controllo se la nuova proiezione Ë contenuta temporalmente da una proiezione esistente o viene intersecata da sinistra.
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
			// La prenotazione ad una proiezione Ë possibile entro i 15 minuti successivi all'inizio della proiezione.
			boolean valida = controlloValidit‡TemporaleProiezione(pr.getData(), pr.getOra(), 15); 
			if (valida == false) {
				throw new EccezioneDominio("Non Ë pi˘ possibile effettuare una prenotazione per la proiezione richiesta");
			}
		}		
		return pr;
		
		
	}
	
	private void caricaFilm() {
		Film f1 = new Film("12345", "Le Ali della libert‡", "Frank Darabont", "Tim Robbins, Morgan Freeman, Bob Gunton, ...", 135, 1994, "Ambientato nel Maine "
				+ "del 1947...", "Drammatico", true);
		Film f2 = new Film("54321", "Batman Begins", "Christopher Nolan", "Christian Bale, Michael Caine, Morgan Freeman, ...", 140, 2005, " Il protagonista "
				+ "della vicenda Ë Bruce Wayne...", "Azione", false);
		Film f3 = new Film("19283", "Il Signore degli Anelli: La Compagnia dell'Anello", "Peter Jackson", "Elijah Wood, Sean Astin, Billy Boyd, ...", 178, 2002,
				"La storia ha inizio quando l'Unico Anello, forgiato da Sauron ...", "Avventura", false);
		
		film.put(f1.getCodice(), f1);
		film.put(f2.getCodice(), f2);
		film.put(f3.getCodice(), f3);
	}
	
}
