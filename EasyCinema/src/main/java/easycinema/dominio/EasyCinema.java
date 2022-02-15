package easycinema.dominio;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import easycinema.EccezioneDominio;
import easycinema.IEasyCinema;
import easycinema.SaleFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;


public class EasyCinema implements IEasyCinema {
	private static EasyCinema istanza;
	private List<Prenotazione> prenotazioni;
	private Map<String, Sala> sale;
	private Prenotazione prenotazioneCorrente;
	private Catalogo catalogo;
	private GestoreUtenti gestoreUtenti;
	
	
	private EasyCinema() {
		this.gestoreUtenti = GestoreUtenti.getIstanza();
		prenotazioni = new LinkedList<Prenotazione>();
		sale = new HashMap<String, Sala>();
		caricaSale();		
		catalogo = new Catalogo(sale);		
	}
	
	public static EasyCinema getIstanza() {
		if(istanza == null) {
			istanza = new EasyCinema();
		}
		return istanza;
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
			// La prenotazione ad una proiezione è possibile entro i 15 minuti successivi all'inizio della proiezione.
			boolean valida = Catalogo.controlloValiditaTemporaleProiezione(pr.getData(), pr.getOra(), 15); 
			if (valida == false) {
				throw new EccezioneDominio("Non è più possibile effettuare una prenotazione per la proiezione richiesta");
			}
			
			Cliente clienteCorrente = gestoreUtenti.getClienteCorrente();
			if(clienteCorrente != null) {
				this.prenotazioneCorrente = new Prenotazione(clienteCorrente, pr);	
			}
		}
		else {
			throw new EccezioneDominio("Il codice della proiezione non è valido.");
		}
	}
	
	private Map<String, LinkedList<Integer>> ottieniPostiOccupatiProiezione(Proiezione pr) {
		Set<Integer> poltroneOccupate = new HashSet<Integer>();
		Set<Integer> postazioniDisabiliOccupate = new HashSet<Integer>();
		
		ArrayList<Prenotazione> prenotazioniTotali = new ArrayList<Prenotazione>(prenotazioni);
		if(prenotazioneCorrente != null)
			prenotazioniTotali.add(prenotazioneCorrente);		//così da considerare anche i posti selezionati ma non ancora confermati per la prenotazione corrente.
		
		for (Prenotazione p : prenotazioniTotali) {
			LinkedList<Integer> poltroneOccupatePrenotazione = (LinkedList<Integer>) p.ottieniPostiBiglietti(pr, Poltrona.class);
			poltroneOccupate.addAll(poltroneOccupatePrenotazione);	
			LinkedList<Integer> postazioniDisabiliOccupatePrenotazione = (LinkedList<Integer>) p.ottieniPostiBiglietti(pr, PostazioneDisabile.class);
			postazioniDisabiliOccupate.addAll(postazioniDisabiliOccupatePrenotazione);	
		}
		
		LinkedList<Integer> postazioniDisabiliOccupate_sorted = new LinkedList<Integer>(postazioniDisabiliOccupate);
		Collections.sort(postazioniDisabiliOccupate_sorted);		
		
		LinkedList<Integer> poltroneOccupate_sorted = new LinkedList<Integer>(poltroneOccupate);
		Collections.sort(poltroneOccupate_sorted);		

		Map<String, LinkedList<Integer>> map = Map.of(
			"Poltrone Occupate", poltroneOccupate_sorted,
			"PostazioniDisabili Occupate", postazioniDisabiliOccupate_sorted			
		);
		return map;		
	}
	
	
	public Map<String, LinkedList<Integer>> ottieniPostiDisponibili(Proiezione pr) {
		// se tale metodo viene invocato durante una prenotazione allora uso la proiezione a cui fa riferimento
		// se voglio usarlo nel caso di getStatoSalaProiezione passo come parametro la proiezione di interesse.
		if (pr == null)
			pr = prenotazioneCorrente.getProiezione();
		
		String nomeSala = catalogo.getNomeSalaProiezione(pr);
		Sala s = sale.get(nomeSala);
		int numPoltrone = s.getNumPoltrone();
		int numPostazioniDisabili = s.getNumPostazioniDisabili();
		
		Map<String, LinkedList<Integer>> postiOccupatiProiezione = ottieniPostiOccupatiProiezione(pr);
		
		LinkedList<Integer> postazioniDisabiliOccupate = postiOccupatiProiezione.get("PostazioniDisabili Occupate");
		LinkedList<Integer> poltroneOccupate = postiOccupatiProiezione.get("Poltrone Occupate");
		
		// generazione dei numeri nel range 1 - numPostazioniDisabili (estremo superiore compreso)
		List<Integer> postazioniDisabiliTotali = IntStream.rangeClosed(1, numPostazioniDisabili).boxed().collect(Collectors.toList());
		Set<Integer> postazioniDisabiliDisponibili = new HashSet<Integer>(postazioniDisabiliTotali);
		postazioniDisabiliDisponibili.removeAll(postazioniDisabiliOccupate);
		
		List<Integer> poltroneTotali = IntStream.rangeClosed(numPostazioniDisabili + 1, numPostazioniDisabili + numPoltrone).boxed().collect(Collectors.toList());
		Set<Integer> poltroneDisponibili = new HashSet<Integer>(poltroneTotali);
		poltroneDisponibili.removeAll(poltroneOccupate);
		
		return Map.of("Poltrone Disponibili", new LinkedList<Integer>(poltroneDisponibili),
				"PostazioniDisabili Disponibili", new LinkedList<Integer>(postazioniDisabiliDisponibili)
				);
	
	}
	
	public void aggiungiBiglietto(int numPosto) throws EccezioneDominio {
		// controllo se numPosto è tra quelli disponibili
		Map<String, LinkedList<Integer>> postiDisponibili = ottieniPostiDisponibili(null);
		LinkedList<Integer> poltroneDisponibili = postiDisponibili.get("Poltrone Disponibili");
		LinkedList<Integer> postazioniDisabiliDisponibili = postiDisponibili.get("PostazioniDisabili Disponibili");
		
		if(poltroneDisponibili.contains(numPosto) || postazioniDisabiliDisponibili.contains(numPosto)) {
			String nomeSala = prenotazioneCorrente.getNomeSalaProiezione();
			Sala s = sale.get(nomeSala);
			PostoSala postoSala = s.getPostoSala(numPosto);
			// controllo relativo alla tipologia di posto e tipologia di cliente
			postoSala.verificaCompatibilitàCliente(gestoreUtenti.getClienteCorrente().getDisabile());
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
		if (prenotazioneCorrente != null) {
			double totale = prenotazioneCorrente.getTotale();			
			
			gestoreUtenti.modificaCreditoCliente(-totale);	// - per ottenere una sottrazione dell'import dal credito
			
			prenotazioni.add(prenotazioneCorrente);
			
			String codice = prenotazioneCorrente.getCodice();
			
			prenotazioneCorrente = null;
			
			return codice;
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
			
			Map<String, LinkedList<Integer>> postiOccupatiProiezione = ottieniPostiOccupatiProiezione(pr);
			Map<String, LinkedList<Integer>> postiDisponibiliProiezione = ottieniPostiDisponibili(pr);
			
			return Map.of(
					"Poltrone Occupate", postiOccupatiProiezione.get("Poltrone Occupate"),
					"PostazioniDisabili Occupate", postiOccupatiProiezione.get("PostazioniDisabili Occupate"),
					"Poltrone Disponibili", postiDisponibiliProiezione.get("Poltrone Disponibili"),
					"PostazioniDisabili Disponibili", postiDisponibiliProiezione.get("PostazioniDisabili Disponibili")
				);
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
			Sala s1 = SaleFactory.creaSala("Basic", "Eliotropo", 40, 0, true, false);
			Sala s2 = SaleFactory.creaSala("Basic", "Solidago", 100, 10, false, true);
			Sala s3 = SaleFactory.creaSala("Luxe", "Indaco", 75, 1, true, true);
			
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

	@Override
	public String autenticaUtente(String username, String password) {
		return null;
	}

	@Override
	public void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo, boolean disabile) throws EccezioneDominio {
		gestoreUtenti.nuovoCliente(codiceFiscale, nome, cognome, indirizzo, disabile);
	}
	
	@Override
	public void nuovoFilm(String codice, String titolo, String regia, String cast, int durata, int anno, String trama, String genere, boolean topFilm) throws EccezioneDominio {
		catalogo.nuovoFilm(codice, titolo, regia, cast, durata, anno, trama, genere, topFilm);
	}

	@Override
	public void nuovaSala(String tipologiaSala, String nome, int numPoltrone, int numPostazioniDisabili, boolean _2d,
			boolean _3d) throws EccezioneDominio {
		if(sale.get(nome) != null)
			throw new EccezioneDominio("Esiste già una sala con lo stesso nome!");
		
		Sala s = SaleFactory.creaSala(tipologiaSala, nome, numPoltrone, numPostazioniDisabili, _2d, _3d);
		sale.put(s.getNome(), s);
	}

	@Override
	public void disdiciPrenotazione(String codice) throws EccezioneDominio {
		// ricerca prenotazione avente per codice quello inserito
		Prenotazione prenotazione = null;
		for (Prenotazione p : prenotazioni) {
			if (p.getCodice().equals(codice)) {
				prenotazione = p;
			}
		}
		
		if (prenotazione != null) {
			// controllo se è stata effettuata da colui che ne richiede l'annullamento
			Cliente clienteCorrente = gestoreUtenti.getClienteCorrente();
			Cliente clientePrenotazione = prenotazione.getCliente();
			if (clienteCorrente.equals(clientePrenotazione)) {
				// controllo sul margine temporale di preavviso: 2 ore.
				Proiezione proiezione = prenotazione.getProiezione();
				boolean valida = Catalogo.controlloValiditaTemporaleProiezione(proiezione.getData(), proiezione.getOra(), -120); 
				if (valida) {
					// rimborso cliente
					double totalePrenotazione = prenotazione.getTotale();
					gestoreUtenti.modificaCreditoCliente(totalePrenotazione);
					// eliminazione prenotazione
					prenotazioni.remove(prenotazione);
				}
				else {
					throw new EccezioneDominio("Non è più possibile disdire la prenotazione richiesta (sono richieste 2 ore di preavviso).");
				}
			}
			else {
				throw new EccezioneDominio("La prenotazione può essere annullata solamente dal cliente che l'ha effettuata!");
			}		
			
		}
		else {
			throw new EccezioneDominio("Il codice inserito non corrisponde ad alcuna prenotazione.");
		}		
	}
	
}
