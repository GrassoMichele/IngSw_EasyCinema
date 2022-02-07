package easycinema.interfaccia.text;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import easycinema.interfaccia.text.uc11.ComandoAggiungiBiglietto;
import easycinema.interfaccia.text.uc11.ComandoCalcolaTotalePrenotazione;
import easycinema.interfaccia.text.uc11.ComandoConfermaPrenotazione;
import easycinema.interfaccia.text.uc11.ComandoElencoPrenotazioni;
import easycinema.interfaccia.text.uc11.ComandoNuovaPrenotazione;
import easycinema.interfaccia.text.uc2.ComandoElencoProiezioni;
import easycinema.interfaccia.text.uc2.ComandoNuovaProiezione;
import easycinema.interfaccia.text.uc3_4.ComandoGetPrenotazioniProiezione;
import easycinema.interfaccia.text.uc3_4.ComandoGetProiezioniPerData;

public class Parser {
	private ElencoComandi comandi;
	private static Parser singleton;
	
	private Parser() {
		comandi = new ElencoComandi();
	}
	
	public static Parser getInstance() {
		if (singleton==null)
			singleton=new Parser();
		return singleton;
	}
	
	public String read() {
	    String inputLine = "";
	
	    System.out.print(" > ");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    try {
	        inputLine = reader.readLine();
	    }
	    catch(java.io.IOException exc) {
	        System.out.println ("Errore durante la lettura: " + exc.getMessage());
	    }
		return inputLine;
	}
		
	public Comando getComando(int console) {
	    String parola = read();
		Comando comando = null;
		
		if(comandi.comandoValido(parola,console)) {
			/* CONSOLE PRINCIPALE */
			if (console == ElencoComandi.HOME){
				if (parola.equals("1"))
					comando = new ComandoNuovaProiezione();
				if (parola.equals("2"))
					comando = new ComandoNuovaPrenotazione();
				if (parola.equals("3"))
					comando = new ComandoElencoProiezioni();
				if (parola.equals("4"))
					comando = new ComandoElencoPrenotazioni();
				if (parola.equals("5"))
					comando = new ComandoGetPrenotazioniProiezione();
				if (parola.equals("6"))
					comando = new ComandoGetProiezioniPerData();
			}
			/* CONSOLE NUOVA PRENOTAZIONE */
			if (console == ElencoComandi.NUOVA_PRENOTAZIONE){
				if (parola.equals("1"))
					comando = new ComandoAggiungiBiglietto();
				if (parola.equals("2"))
					comando = new ComandoCalcolaTotalePrenotazione();
			}
			/* CONSOLE CONFERMA PRENOTAZIONE */
			if (console == ElencoComandi.CONFERMA_PRENOTAZIONE){
				if (parola.equals("1"))
					comando = new ComandoConfermaPrenotazione();
			}
			
		
			/* TORNA AL MENU' PRECEDENTE O ESCI */
			if (parola.equals("0"))
				comando = new ComandoEsci();
	   } else comando = new ComandoNonValido();
		
	   return comando;
	}
}
