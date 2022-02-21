package easycinema.dominio;

import java.time.LocalTime;

//PromoFasciaOraria ha il ruolo di Leaf nel design pattern Composite
public class PromoFasciaOraria extends Promo {
	private LocalTime inizioFasciaOraria;
	private LocalTime fineFasciaOraria;

	
	public PromoFasciaOraria(LocalTime inizioFasciaOraria, LocalTime fineFasciaOraria, int scontoPercentuale) throws EccezioneDominio {
		super(scontoPercentuale);
		setFasciaOraria(inizioFasciaOraria, fineFasciaOraria);
	}
	
	private void setFasciaOraria(LocalTime inizioFasciaOraria, LocalTime fineFasciaOraria) throws EccezioneDominio {
		if (inizioFasciaOraria.isBefore(fineFasciaOraria)) {
			this.inizioFasciaOraria = inizioFasciaOraria;
			this.fineFasciaOraria = fineFasciaOraria;
		}
		else 
			throw new EccezioneDominio("La fine della fascia oraria ne precede l'inizio.");
	}
	

	@Override
	public boolean controlloPromoApplicabile(Prenotazione prenotazione) {
		Proiezione proiezione = prenotazione.getProiezione();
		LocalTime oraInizioProiezione = proiezione.getOra();
		
		boolean proiezioneDopoInizioFasciaOraria = oraInizioProiezione.compareTo(inizioFasciaOraria) >= 0;
		boolean proiezionePrimaFineFasciaOraria = oraInizioProiezione.compareTo(fineFasciaOraria) <= 0;
		
		return (proiezioneDopoInizioFasciaOraria && proiezionePrimaFineFasciaOraria);
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- Promozione Fascia oraria (");
		result.append("inizio fascia oraria: " + inizioFasciaOraria);
		result.append(", fine fascia oraria: " + fineFasciaOraria);
		result.append(", " + super.toString());
		result.append(")");
		return result.toString();
	}
}
