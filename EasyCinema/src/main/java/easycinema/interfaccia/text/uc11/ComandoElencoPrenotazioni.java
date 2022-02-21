package easycinema.interfaccia.text.uc11;

import java.util.List;

import easycinema.dominio.Prenotazione;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;


public class ComandoElencoPrenotazioni extends Comando {
	
	public static final String codiceComando="3";
	public static final String descrizioneComando="Visualizza tutte le PRENOTAZIONI";

   	public String getCodiceComando() {
		return codiceComando;
	}
	
   	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui(IEasyCinema easyCinema) {
		List<Prenotazione> prenotazioni = easyCinema.getPrenotazioni();
		
		if (prenotazioni.size()!=0) {
			System.out.println("\nElenco prenotazioni: ");			
			for (Prenotazione p : prenotazioni) {
				System.out.println(p);
			}
		}
		else {
			System.out.println("Nessuna prenotazione esistente.");		
		}
		
	}
	
}
