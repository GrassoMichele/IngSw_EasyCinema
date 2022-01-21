package easycinema.interfaccia.text;

import easycinema.dominio.EasyCinema;


public abstract class Comando {
	
	public boolean exit = false;
	
	public abstract String getCodiceComando();
	
	public abstract String getDescrizioneComando();
	
	public abstract void esegui(EasyCinema easyCinema);
	
}
