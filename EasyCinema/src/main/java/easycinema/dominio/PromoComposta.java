package easycinema.dominio;

import java.util.ArrayList;
import java.util.List;

//PromoComposite ha il ruolo di Composite nel design pattern Composite
public class PromoComposta implements Promozione {
	private List<Promozione> promozioni;
	
	
	public PromoComposta() {
		promozioni = new ArrayList<Promozione>();
	}

	@Override
	public double calcolaSconto(Prenotazione prenotazione) {
		double totalePrenotazione = prenotazione.getTotale();
		
		double scontoTotale = 0;
		for (Promozione promozione : promozioni)
			scontoTotale += promozione.calcolaSconto(prenotazione);
		
		if(scontoTotale > totalePrenotazione)
			scontoTotale = totalePrenotazione;
			
		return scontoTotale;
	}

	@Override
	public void aggiungi(Promozione promozione) {
		promozioni.add(promozione);
	}

	@Override
	public void rimuovi(Promozione promozione) {
		promozioni.remove(promozione);		
	}

	@Override
	public List<Promozione> getPromozioni() {
		return promozioni;
	}

}
