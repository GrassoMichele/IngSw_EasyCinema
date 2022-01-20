package easycinema.dominio;

import java.util.LinkedList;
import java.util.List;

public class Prenotazione {
	private String codice;
	private Cliente cliente;
	private Proiezione proiezione;
	private double totale; 
	private List<Biglietto> biglietti;
	
	public Prenotazione(Cliente cliente, Proiezione proiezione) {
		setCodice();
		this.cliente = cliente;
		this.proiezione = proiezione;
		this.biglietti = new LinkedList<Biglietto>();
	}
	
	private void setCodice() {
		codice = proiezione.getCodice() + System.currentTimeMillis();
	}
	
	public Proiezione getProiezione() {
		return proiezione;
	}
	
	public List<Integer> ottieniPostiBiglietti(Proiezione proiezione) {
		List<Integer> postiOccupatiPrenotazione = new LinkedList<Integer>();
		if(this.proiezione == proiezione) {
			int numeroPosto;
			for (Biglietto b : biglietti) {
				numeroPosto = b.otteniPostoSala();
				postiOccupatiPrenotazione.add(numeroPosto);
			}
		}
		return postiOccupatiPrenotazione;
	}
	
	public String getNomeSalaProiezione() {
		return proiezione.getNomeSala();
	}
	
	public void aggiungiBiglietto(PostoSala postoSala) {
		String id = codice + (biglietti.size() + 1);
		Biglietto biglietto = new Biglietto(id, postoSala);
		biglietti.add(biglietto);
	}
	
	public double calcolaTotale() {
		double tariffaProiezione = proiezione.getTariffa();
		double totale = tariffaProiezione * biglietti.size();
		return totale;
	}

	public double getTotale() {
		return totale;
	}

	public String getCodice() {
		return codice;
	}
	
}
