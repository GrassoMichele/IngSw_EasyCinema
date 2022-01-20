package easycinema.dominio;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public class EasyCinema {
	private List<Prenotazione> prenotazioni;
	private Map<String, Sala> sale;
	private Prenotazione prenotazioneCorrente;
	private Catalogo catalogo;
	private Utente utenteCorrente;
	
	
	public EasyCinema() {
		prenotazioni = new LinkedList<Prenotazione>();
		sale = new HashMap<String, Sala>();
		catalogo = new Catalogo();
		
		caricaSale();
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
	
	public String nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio {
		Proiezione pr = catalogo.getProiezione(codiceProiezione);
		
		if (pr != null) {
			Cliente clienteCorrente;
			
			// da rimuovere una volta aggiunto il login
			this.utenteCorrente = new Cliente();
			
			clienteCorrente = (Cliente) utenteCorrente;								
			this.prenotazioneCorrente = new Prenotazione(clienteCorrente, pr);				
			return prenotazioneCorrente.getCodice();
		}
		else {
			throw new EccezioneDominio("Il codice della proiezione non è valido.");
		}
		
	}
	
	public List<Integer> ottieniPostiDisponibili() {
		Proiezione pr = prenotazioneCorrente.getProiezione();
		int numPostiSala = pr.getNumPostiSala();
		
		Set<Integer> postiOccupati = new HashSet<Integer>();
		for (Prenotazione p : prenotazioni) {
			LinkedList<Integer> postiOccupatiPrenotazione = (LinkedList<Integer>) p.ottieniPostiBiglietti(pr);
			postiOccupati.addAll(postiOccupatiPrenotazione);			
		}
				
		// generazione dei numeri nel range 1 - numPostiSala (estremo superiore compreso)
		List<Integer> postiTotali = IntStream.rangeClosed(1, numPostiSala).boxed().collect(Collectors.toList());
		
		Set<Integer> postiDisponibili = new HashSet<Integer>(postiTotali);
		postiDisponibili.removeAll(postiOccupati);
		
		LinkedList<Integer> postiDisponibili_sorted = new LinkedList<Integer>(postiDisponibili);
		Collections.sort(postiDisponibili_sorted);
				
		return postiDisponibili_sorted;
	}
	
	public void aggiungiBiglietto(int numPosto) {
		String nomeSala = prenotazioneCorrente.getNomeSalaProiezione();
		Sala s = sale.get(nomeSala);
		PostoSala postoSala = s.getPostoSala(numPosto);
		prenotazioneCorrente.aggiungiBiglietto(postoSala);		
	}
	
	public double calcolaTotalePrenotazione() {
		double totalePrenotazione = prenotazioneCorrente.calcolaTotale();
		return totalePrenotazione;
	}
	
	public void confermaPrenotazione() {
		prenotazioni.add(prenotazioneCorrente);
		
		double totale = prenotazioneCorrente.getTotale();
		Cliente clienteCorrente = (Cliente) utenteCorrente;	
		double credito = clienteCorrente.getCredito();
		clienteCorrente.setCredito(credito - totale);
	}
	
	private void caricaSale() {
		Sala s1 = new Sala("Eliotropo", 50, true, false);
		Sala s2 = new Sala("Solidago", 100, false, true);
		Sala s3 = new Sala("Indaco", 75, true, true);
		
		sale.put(s1.getNome(), s1);
		sale.put(s2.getNome(), s2);
		sale.put(s3.getNome(), s3);		
	}
	
}
