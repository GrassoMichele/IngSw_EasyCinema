package easycinema.interfaccia.text;

import easycinema.fabrication.IEasyCinema;


public class ComandoEsci extends Comando {
	
	public static final String codiceComando="0";
	public static final String descrizioneComando="Esci";

   	public String getCodiceComando() {
		return codiceComando;
	}
	
   	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui(IEasyCinema easyCinema) {
		//torna al menu precedente oppure esce se non ci sono menu precedenti
	}
	
}
