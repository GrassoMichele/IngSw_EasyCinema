package easycinema.dominio;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
	
	public Prenotazione nuovaPrenotazione(String codiceProiezione) {
		Proiezione proiezione = catalogo.getProiezione(codiceProiezione);
		
		Cliente clienteCorrente;
		
		// da rimuovere una volta aggiunto il login
		this.utenteCorrente = new Cliente();
		
		if (this.utenteCorrente instanceof Cliente) {
			clienteCorrente = (Cliente) utenteCorrente;
		}
		else {
			return null;
		}
				
		this.prenotazioneCorrente = new Prenotazione(clienteCorrente, proiezione);	
		
		return prenotazioneCorrente;
	}
	
	public List<Integer> ottieniPostiDisponibili() {
		Proiezione pr = prenotazioneCorrente.getProiezione();
		int numPostiSala = pr.getNumPostiSala();
		
		Set<Integer> postiOccupati = new HashSet<Integer>();
		for (int i=0; i<prenotazioni.size(); i++) {
			LinkedList<Integer> postiOccupatiPrenotazione = (LinkedList<Integer>) prenotazioni.get(i).ottieniPostiBiglietti(pr);
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
