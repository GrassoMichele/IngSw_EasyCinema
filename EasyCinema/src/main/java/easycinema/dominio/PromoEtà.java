package easycinema.dominio;

import java.time.LocalDate;

//PromoEtà ha il ruolo di Leaf nel design pattern Composite
public class PromoEtà extends Promo {
	private int etàMinima, etàMassima;

	public PromoEtà(int etàMinima, int etàMassima, int scontoPercentuale) throws EccezioneDominio {
		super(scontoPercentuale);
		setEtàMinimaMassima(etàMinima, etàMassima);		
	}

	private void setEtàMinimaMassima(int etàMinima, int etàMassima) throws EccezioneDominio {
		if (etàMinima >=0 && etàMinima <= etàMassima && etàMassima < 200) {
			this.etàMinima = etàMinima;
			this.etàMassima = etàMassima;
		}
		else
			throw new EccezioneDominio("Età minima e/o massima non valida.");
	}

	@Override
	public boolean controlloPromoApplicabile(Prenotazione prenotazione) {
		Cliente c = prenotazione.getCliente();
		int annoNascita = c.getAnnoNascita();
		int età = LocalDate.now().getYear() - annoNascita;
	
		return (età >= etàMinima && età <= etàMassima);
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- Promozione Età (");
		result.append("età minima: " + etàMinima);
		result.append(", età massima: " + etàMassima);
		result.append(", " + super.toString());
		result.append(")");
		return result.toString();
	}

}
