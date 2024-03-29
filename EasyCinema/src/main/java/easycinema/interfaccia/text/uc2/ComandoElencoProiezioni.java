package easycinema.interfaccia.text.uc2;

import easycinema.dominio.Proiezione;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;

public class ComandoElencoProiezioni extends Comando{

	public static final String codiceComando="2";
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
	public void esegui(IEasyCinema easyCinema) {
		System.out.println("*** ELENCO PROIEZIONI ***");
		for (Proiezione pr : easyCinema.getProiezioni().values()) {
			System.out.println("   " + pr);
		}
	}
	
}
