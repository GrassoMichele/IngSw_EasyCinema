package easycinema.dominio;

import java.util.List;

// Promozione ha il ruolo di Component nel design pattern Composite
public interface Promozione {
	double calcolaSconto(Prenotazione prenotazione);

	void aggiungi(Promozione promozione);
	void rimuovi(Promozione promozione);	 
	List<Promozione> getPromozioni();
}
