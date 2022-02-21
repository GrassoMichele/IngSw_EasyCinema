package easycinema.dominio;

import java.time.LocalDate;

//PromoEt� ha il ruolo di Leaf nel design pattern Composite
public class PromoEt� extends Promo {
	private int et�Minima, et�Massima;

	public PromoEt�(int et�Minima, int et�Massima, int scontoPercentuale) throws EccezioneDominio {
		super(scontoPercentuale);
		setEt�MinimaMassima(et�Minima, et�Massima);		
	}

	private void setEt�MinimaMassima(int et�Minima, int et�Massima) throws EccezioneDominio {
		if (et�Minima >=0 && et�Minima <= et�Massima && et�Massima < 200) {
			this.et�Minima = et�Minima;
			this.et�Massima = et�Massima;
		}
		else
			throw new EccezioneDominio("Et� minima e/o massima non valida.");
	}

	@Override
	public boolean controlloPromoApplicabile(Prenotazione prenotazione) {
		Cliente c = prenotazione.getCliente();
		int annoNascita = c.getAnnoNascita();
		int et� = LocalDate.now().getYear() - annoNascita;
	
		return (et� >= et�Minima && et� <= et�Massima);
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- Promozione Et� (");
		result.append("et� minima: " + et�Minima);
		result.append(", et� massima: " + et�Massima);
		result.append(", " + super.toString());
		result.append(")");
		return result.toString();
	}

}
