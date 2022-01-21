package easycinema.dominio;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;


public class EasyCinema {
	private List<Prenotazione> prenotazioni;
	private Map<String, Sala> sale;
	private Prenotazione prenotazioneCorrente;
	private Catalogo catalogo;
	private Utente utenteCorrente;
	
	
	public EasyCinema() {
		prenotazioni = new LinkedList<Prenotazione>();
		sale = new HashMap<String, Sala>();
		caricaSale();		
		catalogo = new Catalogo(sale);		
	}
	
	public void nuovaProiezione(String codice, String codiceFilm, String nomeSala, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio {
		Sala s = sale.get(nomeSala);
		if(s != null) {
			boolean esito = controlloTecnologiaSala(s, _3D);
			if (esito == true) {
				catalogo.nuovaProiezione(codice, codiceFilm, s, data, ora, _3D, tariffaBase);
			}
			else {
				throw new EccezioneDominio("La sala scelta per la proiezione non supporta la tipologia di proiezione (2D/3D) indicata.");
			}
		}
		else {
			throw new EccezioneDominio("Il nome della sala scelta per la proiezione non esiste.");
		}
	}
	
	private boolean controlloTecnologiaSala(Sala sala, boolean _3D) {
		if ((_3D == true && !sala.is_3D()) || (_3D == false && !sala.is_2D())) {
			return false;
		}
		return true;
	}
	
	public void nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio {
		Proiezione pr = catalogo.getProiezione(codiceProiezione);
		
		if (pr != null) {
			Cliente clienteCorrente;
			
			// da rimuovere una volta aggiunto il login
			this.utenteCorrente = new Cliente(10);
			
			clienteCorrente = (Cliente) utenteCorrente;								
			this.prenotazioneCorrente = new Prenotazione(clienteCorrente, pr);				
		}
		else {
			throw new EccezioneDominio("Il codice della proiezione non è valido.");
		}
		
	}
	
	public List<Integer> ottieniPostiDisponibili() {
		Proiezione pr = prenotazioneCorrente.getProiezione();
		int numPostiSala = prenotazioneCorrente.getNumPostiSala();
		
		Set<Integer> postiOccupati = new HashSet<Integer>();
		for (Prenotazione p : prenotazioni) {
			LinkedList<Integer> postiOccupatiPrenotazione = (LinkedList<Integer>) p.ottieniPostiBiglietti(pr);
			postiOccupati.addAll(postiOccupatiPrenotazione);			
		}
		LinkedList<Integer> postiOccupatiPrenotazioneCorrente = (LinkedList<Integer>) prenotazioneCorrente.ottieniPostiBiglietti(pr);
		postiOccupati.addAll(postiOccupatiPrenotazioneCorrente);	
				
		// generazione dei numeri nel range 1 - numPostiSala (estremo superiore compreso)
		List<Integer> postiTotali = IntStream.rangeClosed(1, numPostiSala).boxed().collect(Collectors.toList());
		
		Set<Integer> postiDisponibili = new HashSet<Integer>(postiTotali);
		postiDisponibili.removeAll(postiOccupati);
		
		LinkedList<Integer> postiDisponibili_sorted = new LinkedList<Integer>(postiDisponibili);
		Collections.sort(postiDisponibili_sorted);
				
		return postiDisponibili_sorted;
	}
	
	public void aggiungiBiglietto(int numPosto) throws EccezioneDominio {
		// controllo se numPosto è tra quelli disponibili
		List<Integer> postiDisponibili = ottieniPostiDisponibili();
		if(postiDisponibili.contains(numPosto)) {
			String nomeSala = prenotazioneCorrente.getNomeSalaProiezione();
			Sala s = sale.get(nomeSala);
			PostoSala postoSala = s.getPostoSala(numPosto);
			prenotazioneCorrente.aggiungiBiglietto(postoSala);	
		}
		else {
			throw new EccezioneDominio("Il posto selezionato non è tra quelli disponibili");
		}
	}
	
	public double calcolaTotalePrenotazione() throws EccezioneDominio {
		double totalePrenotazione = prenotazioneCorrente.calcolaTotale();
		
		if (totalePrenotazione == 0) 	// nessun biglietto acquistato
			throw new EccezioneDominio("Nessun biglietto acquistato!");
		
		return totalePrenotazione;
	}
	
	public String confermaPrenotazione() throws EccezioneDominio {		
		double totale = prenotazioneCorrente.getTotale();
		Cliente clienteCorrente = (Cliente) utenteCorrente;	
		double credito = clienteCorrente.getCredito();		
		clienteCorrente.setCredito(credito - totale);
		
		prenotazioni.add(prenotazioneCorrente);
		
		return prenotazioneCorrente.getCodice();
	}
	
	private void caricaSale() {
		Sala s1 = new Sala("Eliotropo", 50, true, false);
		Sala s2 = new Sala("Solidago", 100, false, true);
		Sala s3 = new Sala("Indaco", 75, true, true);
		
		sale.put(s1.getNome(), s1);
		sale.put(s2.getNome(), s2);
		sale.put(s3.getNome(), s3);		
	}

	public Map<String, Sala> getSale() {
		return sale;
	}
	
	public Map<String, Film> getFilm() {
		return catalogo.getFilm();
	}
	
	public Map<String, Proiezione> getProiezioni() {
		return catalogo.getProiezioni();
	}

	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}
	
}
