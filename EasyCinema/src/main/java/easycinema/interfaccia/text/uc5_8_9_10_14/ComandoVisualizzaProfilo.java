package easycinema.interfaccia.text.uc5_8_9_10_14;

import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;

public class ComandoVisualizzaProfilo extends Comando{

	public static final String codiceComando="7";
	public static final String descrizioneComando="Visualizza PROFILO";

	
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
		System.out.println("*** PROFILO utente ***");
		System.out.println(easyCinema.visualizzaProfilo());
	}

}
