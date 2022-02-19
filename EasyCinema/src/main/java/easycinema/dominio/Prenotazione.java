package easycinema.dominio;

import java.util.LinkedList;
import java.util.List;

public class Prenotazione {
	private String codice;
	private Cliente cliente;
	private Proiezione proiezione;
	private double totale; 
	private List<Biglietto> biglietti;
	private Promozione promozione;
	
	public Prenotazione(Cliente cliente, Proiezione proiezione) {
		this.cliente = cliente;
		this.proiezione = proiezione;
		this.biglietti = new LinkedList<Biglietto>();
		setCodice();
	}
	
	private void setCodice() {
		codice = proiezione.getCodice() + System.currentTimeMillis();
	}
	
	public void setPromozione(Promozione promozione) {
		this.promozione = promozione; 
	}
	
	public Proiezione getProiezione() {
		return proiezione;
	}
	
	public int getNumPostiSala() {
		return proiezione.getNumPostiSala();
	}
	
	@SuppressWarnings("rawtypes")
	public List<Integer> ottieniPostiBiglietti(Proiezione proiezione, Class tipologiaPosto) {
		List<Integer> postiOccupatiPrenotazione = new LinkedList<Integer>();
		if(this.proiezione == proiezione) {
			int numeroPosto;
			for (Biglietto b : biglietti) {
				numeroPosto = b.otteniNumeroPostoSala(tipologiaPosto);
				if(numeroPosto == 0)		// il biglietto non fa riferimento alla tipologia di posto di interesse
					continue;
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
	
	public double calcolaTotale() throws EccezioneDominio {
		if (biglietti.size() != 0) {
			double tariffaProiezione = proiezione.getTariffa();
			double totaleNoPromo = tariffaProiezione * biglietti.size();		
			totaleNoPromo = (double) Math.round(totaleNoPromo * 100d) / 100d;		
			totale = totaleNoPromo;
			
			if (promozione != null) {
				double sconto = promozione.calcolaSconto(this);
				totale -= sconto;	
			}			
			return totale;
		}
		else
			throw new EccezioneDominio("Nessun biglietto acquistato!");	
	}
	
	public double getTotale() {
		return totale;
	}

	public String getCodice() {
		return codice;
	}	
	
	public Cliente getCliente() {
		return cliente;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- ");
		result.append("Codice: " + codice);
		result.append(", Cliente: (" + cliente + ")");
		result.append(", Proiezione: " + proiezione.getCodice());
		result.append(", Totale: " + totale);
		result.append(", Biglietti: ");
		for (Biglietto biglietto : biglietti) {
			result.append("(id: " + biglietto.getId() + ", posto: " + biglietto.otteniNumeroPostoSala() + ") ");
		}		
		return result.toString();
	}
	
}
