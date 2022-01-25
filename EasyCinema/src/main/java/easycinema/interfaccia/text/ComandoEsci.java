package easycinema.interfaccia.text;

import easycinema.dominio.EasyCinema;


public class ComandoEsci extends Comando {
	
	public static final String codiceComando="0";
	public static final String descrizioneComando="Esci";

   	public String getCodiceComando() {
		return codiceComando;
	}
	
   	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	public void esegui(EasyCinema easyCinema) {
		//torna al menu precedente oppure esce se non ci sono menu precedenti
	}
	
}
