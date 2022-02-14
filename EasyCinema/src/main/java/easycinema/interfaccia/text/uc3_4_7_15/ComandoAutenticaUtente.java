package easycinema.interfaccia.text.uc3_4_7_15;

import easycinema.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Console;
import easycinema.interfaccia.text.ElencoComandi;
import easycinema.interfaccia.text.Parser;

public class ComandoAutenticaUtente extends Comando{

	public static final String codiceComando="1";
	public static final String descrizioneComando="Accedi";

	
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
		System.out.println("\nUsername: ");
		String username = Parser.getInstance().read();
		System.out.println("Password: ");
		String password = Parser.getInstance().read();
		
		String accesso = easyCinema.autenticaUtente(username, password);
		
		if(accesso!="Fallita") {
			System.out.println("\nAccesso effettuato in qualità di " + accesso + "\n");
			
			Console home;			
			int tipologiaConsole = accesso.equals("titolare")? ElencoComandi.HOME_TITOLARE : ElencoComandi.HOME_CLIENTE; 
			
			home = new Console(tipologiaConsole, "HOME - " + accesso);
			home.start(easyCinema);				
		}
		else {
			System.out.println("\nAutenticazione " + accesso);
		}
		
		
	}

}
