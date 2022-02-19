package easycinema.fabrication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Prenotazione;
import easycinema.dominio.PromoComposta;
import easycinema.dominio.PromoDisabile;
import easycinema.dominio.PromoEt‡;
import easycinema.dominio.PromoFasciaOraria;
import easycinema.dominio.PromoGiorno;
import easycinema.dominio.Promozione;
import easycinema.dominio.Promo;

public class GestorePromozioni {
	private static GestorePromozioni istanza;
	private List<Promo> promozioni;
	
	private GestorePromozioni() {
		promozioni = new ArrayList<Promo>();		
	}
	
	public static GestorePromozioni getIstanza() {
		if(istanza == null) {
			istanza = new GestorePromozioni();
		}
		return istanza;
	}	
	
	public void creaPromozione(String tipologia, List<String> condizione, int percentualeSconto) throws EccezioneDominio {
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		DateTimeFormatter oraFormatter = DateTimeFormatter.ofPattern("H:mm");
		
		Promo promo = null;
		
		if (percentualeSconto < 1 || percentualeSconto > 100)
			throw new EccezioneDominio("La percentuale di sconto deve essere compresa tra 1 e 100");
				
		switch (tipologia) {
			case "Disabile": 				
				promo = new PromoDisabile(percentualeSconto);
				break;
				
			case "Giorno": 
				try {
					LocalDate giorno = LocalDate.parse(condizione.get(0), dataFormatter);					
					if (condizione.get(1).length() == 1) {
						char sesso = condizione.get(1).charAt(0);
						promo = new PromoGiorno(giorno, sesso, percentualeSconto);
						break;
					}						
					else
						throw new EccezioneDominio("Sesso non valido.");	
				}
				catch (DateTimeParseException e) {
					throw new EccezioneDominio("Formato del giorno non corretto.");
				} 		
				
			case "Et‡": 
				try {
					int et‡Minima = Integer.parseInt(condizione.get(0));
					int et‡Massima = Integer.parseInt(condizione.get(1));
					
					promo = new PromoEt‡(et‡Minima, et‡Massima, percentualeSconto);	
					break;
				}
				catch (NumberFormatException e) {
					throw new EccezioneDominio("Et‡ minima e/o massima non valida.");
				}			
				
			case "FasciaOraria": 
				try {
					LocalTime inizioFasciaOraria = LocalTime.parse(condizione.get(0), oraFormatter);
					LocalTime fineFasciaOraria = LocalTime.parse(condizione.get(1), oraFormatter);
					promo = new PromoFasciaOraria(inizioFasciaOraria, fineFasciaOraria, percentualeSconto);
					break;
				}
				catch (DateTimeParseException e) {
					throw new EccezioneDominio("Formato inizio e/o fine fascia oraria non corretto.");
				} 
				
			default:
				throw new EccezioneDominio("Tipologia di promozione non esistente!");	
		}
		
		if (promo != null)
			promozioni.add(promo);
	}
	
	
	public Promozione associaPromozione(Prenotazione prenotazione) {
		Promozione promozioneComposta = new PromoComposta();
				
		for (Promo promo : promozioni) {
			if (promo.controlloPromoApplicabile(prenotazione))
				promozioneComposta.aggiungi(promo);
		}
		
		return promozioneComposta;		
	}
	
}
