package easycinema.interfaccia.text;

import easycinema.fabrication.IEasyCinema;


public class ComandoNonValido extends Comando {
	
	public static final String codiceComando="-1";
	public static final String descrizioneComando="comando non valido";

   	public String getCodiceComando() {
		return codiceComando;
	}
	
   	public String getDescrizioneComando() {
		return descrizioneComando;
	}

    public void esegui(IEasyCinema easyCinema) {
		System.out.println("   COMANDO INESISTENTE");
	}
}
