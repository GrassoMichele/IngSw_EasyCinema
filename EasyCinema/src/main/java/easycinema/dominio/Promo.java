package easycinema.dominio;

import java.util.List;

//Promo ha lo scopo di implementare i metodi a comune tra le varie Leaves del design pattern Composite
public abstract class Promo implements Promozione {
	protected int scontoPercentuale;
	
	public Promo(int scontoPercentuale) {
		this.scontoPercentuale = scontoPercentuale;
	}
	
	public abstract boolean controlloPromoApplicabile(Prenotazione prenotazione);
	
	@Override
	public double calcolaSconto(Prenotazione prenotazione) {
		double sconto = 0;

		boolean applicabile = controlloPromoApplicabile(prenotazione);
		if(applicabile) {
			// ciascuno sconto è calcolato sul totale della prenotazione (senza considerare l'influenza di eventuali altri sconti). 
			double totalePrenotazione = prenotazione.getTotale();
			sconto = totalePrenotazione * scontoPercentuale / 100;
		}		
		return sconto;
	}
	
	@Override
	public void aggiungi(Promozione promozione) {
	}
	
	@Override
	public void rimuovi(Promozione promozione) {
	}
	
	@Override
	public List<Promozione> getPromozioni() {
		return null;
	}

	@Override
	public String toString() {
		return "Sconto percentuale: " + scontoPercentuale;
	}
}
