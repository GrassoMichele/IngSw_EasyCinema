package easycinema.interfaccia.text.uc1_6_12_13;

import java.util.List;

import easycinema.IEasyCinema;
import easycinema.dominio.Prenotazione;
import easycinema.interfaccia.text.Comando;

public class ComandoVisualizzaPrenotazioni extends Comando {

	public static final String codiceComando="5";
	public static final String descrizioneComando="Visualizza Prenotazioni";

	
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
			System.out.println("\nElenco prenotazioni: ");		
			for (Prenotazione p : prenotazioni) {
				System.out.println(p);
			}	
		}
		else
			System.out.println("\nNessuna prenotazione presente.\n");
	}
}
