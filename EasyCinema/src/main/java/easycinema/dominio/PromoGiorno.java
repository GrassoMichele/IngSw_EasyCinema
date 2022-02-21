package easycinema.dominio;

import java.time.LocalDate;

//PromoGiorno ha il ruolo di Leaf nel design pattern Composite
public class PromoGiorno extends Promo {
	private LocalDate giorno;
	private char sesso;
	
	
	public PromoGiorno(LocalDate giorno, char sesso, int scontoPercentuale) throws EccezioneDominio {
		super(scontoPercentuale);
		setGiorno(giorno);
		setSesso(sesso);
	}

	private void setGiorno(LocalDate giorno) throws EccezioneDominio {
		// se la data non è passata
		if (giorno.compareTo(LocalDate.now()) >= 0)
			this.giorno = giorno;
		else
			throw new EccezioneDominio("Il giorno inserito è un giorno passato!");
	}

	private void setSesso(char sesso) throws EccezioneDominio {
		if(sesso == 'M' || sesso == 'F' || sesso == 'N')
			this.sesso = sesso;
		else
			throw new EccezioneDominio("ERRORE: Le opzioni possibili sono: M(aschile), F(emminile) o N(essuno)");
	}
	

	@Override
	public boolean controlloPromoApplicabile(Prenotazione prenotazione) {
		Cliente c = prenotazione.getCliente();
		char sessoCliente = c.getSesso();
		
		Proiezione proiezione = prenotazione.getProiezione();
		LocalDate giornoProiezione = proiezione.getData();
		
		if(giornoProiezione.equals(giorno)) {
			// è richiesto il filtraggio
			if (sesso != 'N') {
				if(sessoCliente == sesso)
					return true;
			}
			else 
				return true;
		}
		return false;		
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- Promozione Giorno (");
		result.append("giorno: " + giorno);
		result.append(", filtro sul sesso: " + sesso);
		result.append(", " + super.toString());
		result.append(")");
		return result.toString();
	}

}
