package easycinema.interfaccia.text.uc11;

import easycinema.dominio.EasyCinema;
import easycinema.dominio.Prenotazione;
import easycinema.dominio.Proiezione;
import easycinema.interfaccia.text.Comando;


public class ComandoElencoPrenotazioni extends Comando {
	
	public static final String codiceComando="4";
	public static final String descrizioneComando="Visualizza le PRENOTAZIONI";

   	public String getCodiceComando() {
		return codiceComando;
	}
	
   	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui(EasyCinema easyCinema) {
		System.out.println("\nElenco prenotazioni: ");
		for (Prenotazione p : easyCinema.getPrenotazioni()) {
			System.out.println(p);
		}
	}
	
}
