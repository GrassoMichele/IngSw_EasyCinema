package easycinema.interfaccia.text.uc11;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;

public class ComandoConfermaPrenotazione extends Comando {
	
	public static final String codiceComando="1";
	public static final String descrizioneComando="Conferma prenotazione";
	
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
		String codicePrenotazione;
		try {
			codicePrenotazione = easyCinema.confermaPrenotazione();
			System.out.println("PRENOTAZIONE EFFETTUATA CON SUCCESSO!"); 
			System.out.println("Il codice della tua prenotazione è: " + codicePrenotazione);
		} 
		catch (EccezioneDominio e) {
			System.out.println("\n" + e.getMessage());
		} 
		finally {
			exit = true;
		}		
	}
}
