package easycinema.interfaccia.text.uc2;

import easycinema.dominio.EasyCinema;
import easycinema.dominio.Proiezione;
import easycinema.interfaccia.text.Comando;

public class ComandoElencoProiezioni extends Comando{

	public static final String codiceComando="3";
	public static final String descrizioneComando="Visualizza le PROIEZIONI";

	
	@Override
	public String getCodiceComando() {
		return codiceComando;
	}

	@Override
	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	@Override
	public void esegui(EasyCinema easyCinema) {
		System.out.println("\nElenco proiezioni: ");
		for (Proiezione pr : easyCinema.getProiezioni().values()) {
			System.out.println(pr);
		}
	}
	
}
