package easycinema.dominio;

//PromoDisabile ha il ruolo di Leaf nel design pattern Composite
public class PromoDisabile extends Promo {

	public PromoDisabile(int scontoPercentuale) {
		super(scontoPercentuale);
	}
	
	@Override
	public boolean controlloPromoApplicabile(Prenotazione prenotazione) {
		Cliente c = prenotazione.getCliente();
		boolean disabile = c.getDisabile();		
		return disabile;
	}

}
