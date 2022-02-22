package easycinema.interfaccia.text.uc1_6_12_13;

import java.util.List;

import easycinema.dominio.Prenotazione;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;

public class ComandoVisualizzaPrenotazioni extends Comando {

	public static final String codiceComando="5";
	public static final String descrizioneComando="Visualizza le PRENOTAZIONI effettuate";

	
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
		List<Prenotazione> prenotazioni = easyCinema.visualizzaPrenotazioni();
		
		if(prenotazioni.size() != 0) {
			System.out.println("*** Elenco PRENOTAZIONI ***");		
			for (Prenotazione p : prenotazioni) {
				System.out.println("   " + p);
			}	
		}
		else
			System.out.println("Nessuna prenotazione presente.");
	}
}
