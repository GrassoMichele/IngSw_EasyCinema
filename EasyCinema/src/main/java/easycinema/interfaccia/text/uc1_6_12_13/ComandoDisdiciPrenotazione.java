package easycinema.interfaccia.text.uc1_6_12_13;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoDisdiciPrenotazione extends Comando {

	public static final String codiceComando="4";
	public static final String descrizioneComando="Disdici una PRENOTAZIONE";

	
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
		try {
			System.out.println("Codice prenotazione: ");
			String codice = Parser.getInstance().read();
			
			System.out.println();
						
			easyCinema.disdiciPrenotazione(codice);
			
			System.out.println("LA PRENOTAZIONE È STATA ANNULLATA. Rimborso effettuato!");
		}
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		
	}
}
