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

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- Promozione Disabilità (");
		result.append(super.toString());
		result.append(")");
		return result.toString();
	}

}
