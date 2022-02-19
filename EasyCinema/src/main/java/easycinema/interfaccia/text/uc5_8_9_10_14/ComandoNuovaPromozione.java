package easycinema.interfaccia.text.uc5_8_9_10_14;

import java.util.ArrayList;
import java.util.List;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovaPromozione extends Comando{

	public static final String codiceComando="10";
	public static final String descrizioneComando="Aggiungi una nuova PROMOZIONE";

	
	@Override
	public String getCodiceComando() {
		return codiceComando;
	}

	@Override
	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	@Override
	public void esegui(IEasyCinema easyCinema) {
		
		String[] tipologiePromozioni = {"Disabile", "Giorno", "Et‡", "FasciaOraria"} ;
		
		System.out.println("\n\nTipologie promozioni: ");
		for (int i = 0; i < tipologiePromozioni.length; i++) {
			System.out.println(i+1 + ") " + tipologiePromozioni[i]);
		}
		System.out.println("Seleziona: ");
		String selezione = Parser.getInstance().read();
		System.out.println("\n");
		
		try {
			int numeroPromo = Integer.parseInt(selezione);
			String tipologiaPromo = tipologiePromozioni[numeroPromo-1];		
			List<String> condizione = new ArrayList<String>();
			
			switch(tipologiaPromo) {
			case "Disabile":
				break;

			case "Giorno":
				System.out.println("Giorno (dd/mm/yyyy): ");
				String giorno = Parser.getInstance().read();
				System.out.println("Filtro sul sesso (M/F/N(essuno)): ");
				String sesso = Parser.getInstance().read();
				condizione.add(giorno);
				condizione.add(sesso);				
				break;
				
			case "Et‡":
				System.out.println("Et‡ minima: ");
				String et‡Minima = Parser.getInstance().read();
				System.out.println("Et‡ massima: ");
				String et‡Massima = Parser.getInstance().read();
				condizione.add(et‡Minima);
				condizione.add(et‡Massima);
				break;
				
			case "FasciaOraria":
				System.out.println("Inizio fascia oraria (hh:mm): ");
				String inizioFasciaOraria = Parser.getInstance().read();
				System.out.println("Fine fascia oraria (hh:mm): ");
				String fineFasciaOraria = Parser.getInstance().read();
				condizione.add(inizioFasciaOraria);
				condizione.add(fineFasciaOraria);
				break;				
			}
			
			System.out.println("Percentuale sconto (1-100): ");
			int percentualeSconto = Integer.parseInt(Parser.getInstance().read());
			
			easyCinema.nuovaPromozione(tipologiaPromo, condizione, percentualeSconto);
			
			System.out.println("Promozione creata con successo!");
			
		}
		catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			System.out.println("ERRORE: Scelta non valida!");
		}
		catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}

}
