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
import java.util.ArrayList;
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
	
	boolean controlloTecnologiaSala(Sala sala, boolean _3D) {
		if ((_3D == true && !sala.is_3D()) || (_3D == false && !sala.is_2D())) {
			return false;
		}
		return true;
	}
	
	public void nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio {
		Proiezione pr = catalogo.getProiezione(codiceProiezione);
		
		if (pr != null) {
			// La prenotazione ad una proiezione � possibile entro i 15 minuti successivi all'inizio della proiezione.
			boolean valida = Catalogo.controlloValiditaTemporaleProiezione(pr.getData(), pr.getOra(), 15); 
			if (valida == false) {
				throw new EccezioneDominio("Non � pi� possibile effettuare una prenotazione per la proiezione richiesta");
			}
	
			Cliente clienteCorrente;
			
			// da rimuovere una volta aggiunto il login
			this.utenteCorrente = new Cliente(10);
			
			clienteCorrente = (Cliente) utenteCorrente;								
			this.prenotazioneCorrente = new Prenotazione(clienteCorrente, pr);				
		}
		else {
			throw new EccezioneDominio("Il codice della proiezione non � valido.");
		}
		
	}
	private Map<String, LinkedList<Integer>> ottieniSuddivisionePostiProiezione(Proiezione pr, int numPostiSala, boolean prenotazioneInCorso) {
		Set<Integer> postiOccupati = new HashSet<Integer>();
		for (Prenotazione p : prenotazioni) {
			LinkedList<Integer> postiOccupatiPrenotazione = (LinkedList<Integer>) p.ottieniPostiBiglietti(pr);
			postiOccupati.addAll(postiOccupatiPrenotazione);			
		}
		
		if (prenotazioneInCorso) {
			LinkedList<Integer> postiOccupatiPrenotazioneCorrente = (LinkedList<Integer>) prenotazioneCorrente.ottieniPostiBiglietti(pr);
			postiOccupati.addAll(postiOccupatiPrenotazioneCorrente);
		}
		
		// generazione dei numeri nel range 1 - numPostiSala (estremo superiore compreso)
		List<Integer> postiTotali = IntStream.rangeClosed(1, numPostiSala).boxed().collect(Collectors.toList());

		Set<Integer> postiDisponibili = new HashSet<Integer>(postiTotali);
		postiDisponibili.removeAll(postiOccupati);

		LinkedList<Integer> postiOccupati_sorted = new LinkedList<Integer>(postiOccupati);
		Collections.sort(postiOccupati_sorted);
		
		LinkedList<Integer> postiDisponibili_sorted = new LinkedList<Integer>(postiDisponibili);
		Collections.sort(postiDisponibili_sorted);
		
		Map<String, LinkedList<Integer>> map = Map.of("Disponibili", postiDisponibili_sorted, "Occupati", postiOccupati_sorted);
		return map;
	}
	
	
	public List<Integer> ottieniPostiDisponibili() {
		Proiezione pr = prenotazioneCorrente.getProiezione();
		int numPostiSala = prenotazioneCorrente.getNumPostiSala();
		
		Map<String, LinkedList<Integer>> suddivisionePosti = ottieniSuddivisionePostiProiezione(pr, numPostiSala, true);
				
		return suddivisionePosti.get("Disponibili");
	}
	
	public void aggiungiBiglietto(int numPosto) throws EccezioneDominio {
		// controllo se numPosto � tra quelli disponibili
		List<Integer> postiDisponibili = ottieniPostiDisponibili();
		if(postiDisponibili.contains(numPosto)) {
			String nomeSala = prenotazioneCorrente.getNomeSalaProiezione();
			Sala s = sale.get(nomeSala);
			PostoSala postoSala = s.getPostoSala(numPosto);
			prenotazioneCorrente.aggiungiBiglietto(postoSala);	
		}
		else {
			throw new EccezioneDominio("Il posto selezionato non � tra quelli disponibili");
		}
	}
	
	public double calcolaTotalePrenotazione() throws EccezioneDominio {
		double totalePrenotazione = prenotazioneCorrente.calcolaTotale();
		
		if (totalePrenotazione == 0) 	// nessun biglietto acquistato
			throw new EccezioneDominio("Nessun biglietto acquistato!");
		
		return totalePrenotazione;
	}
	
	public String confermaPrenotazione() throws EccezioneDominio {	
		if (prenotazioneCorrente != null) {
			double totale = prenotazioneCorrente.getTotale();
			Cliente clienteCorrente = (Cliente) utenteCorrente;	
			double credito = clienteCorrente.getCredito();		
			clienteCorrente.setCredito(credito - totale);
			
			prenotazioni.add(prenotazioneCorrente);
			
			return prenotazioneCorrente.getCodice();
		}
		else {
			throw new EccezioneDominio("Nessuna prenotazione in corso!");
		}		
	}
	
	public List<Prenotazione> getPrenotazioniProiezione(String codiceProiezione) throws EccezioneDominio {
		List<Prenotazione> prenotazioniProiezione = new ArrayList<Prenotazione>();
		Proiezione pr = catalogo.getProiezione(codiceProiezione);
		if (pr != null) {
			for (Prenotazione p : prenotazioni) {
				if (p.getProiezione() == pr)
					prenotazioniProiezione.add(p);
			}
			return prenotazioniProiezione;
		}
		else {
			throw new EccezioneDominio("Proiezione non esistente.");
		}
		
	}
	
	public Map<String, LinkedList<Integer>> getStatoSalaProiezione(String codiceProiezione) throws EccezioneDominio {
		Proiezione pr = catalogo.getProiezione(codiceProiezione);
		if (pr != null) {
			String nomeSala = catalogo.getNomeSalaProiezione(pr);
			Sala s = sale.get(nomeSala);
			int numPostiSala = s.getNumPostiTotali();
			
			return ottieniSuddivisionePostiProiezione(pr, numPostiSala, false);
		}
		else {
			throw new EccezioneDominio("Proiezione non esistente.");
		}
	}
	
	public List<Proiezione> getProiezioniPerData(LocalDate data) {
		return catalogo.getProiezioniPerData(data);
	}
	
	
	private void caricaSale() {
		try {
			Sala s1 = new Sala("Eliotropo", 50, true, false);
			Sala s2 = new Sala("Solidago", 100, false, true);
			Sala s3 = new Sala("Indaco", 75, true, true);
			
			sale.put(s1.getNome(), s1);
			sale.put(s2.getNome(), s2);
			sale.put(s3.getNome(), s3);		
		}
		catch (EccezioneDominio e) {}
		
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
