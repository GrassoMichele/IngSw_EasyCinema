package easycinema.interfaccia.text;

import easycinema.interfaccia.text.uc11.ComandoAggiungiBiglietto;
import easycinema.interfaccia.text.uc11.ComandoCalcolaTotalePrenotazione;
import easycinema.interfaccia.text.uc11.ComandoConfermaPrenotazione;
import easycinema.interfaccia.text.uc11.ComandoElencoPrenotazioni;
import easycinema.interfaccia.text.uc11.ComandoNuovaPrenotazione;
import easycinema.interfaccia.text.uc2.ComandoElencoProiezioni;
import easycinema.interfaccia.text.uc2.ComandoNuovaProiezione;
import easycinema.interfaccia.text.uc3_4.ComandoGetPrenotazioniProiezione;
import easycinema.interfaccia.text.uc3_4.ComandoGetProiezioniPerData;

public class ElencoComandi {
	public static final int HOME = 0;
	/*UC 11*/
	public static final int NUOVA_PRENOTAZIONE = 1;
	public static final int CONFERMA_PRENOTAZIONE = 2;
	
	/* MENU' PRINCIPALE */
    private static final String comandiValidiHomeConsole[][] = {
		{ComandoNuovaProiezione.codiceComando,ComandoNuovaProiezione.descrizioneComando},
		{ComandoNuovaPrenotazione.codiceComando,ComandoNuovaPrenotazione.descrizioneComando},
		{ComandoElencoProiezioni.codiceComando,ComandoElencoProiezioni.descrizioneComando},
		{ComandoElencoPrenotazioni.codiceComando,ComandoElencoPrenotazioni.descrizioneComando},
		{ComandoGetPrenotazioniProiezione.codiceComando,ComandoGetPrenotazioniProiezione.descrizioneComando},
		{ComandoGetProiezioniPerData.codiceComando,ComandoGetProiezioniPerData.descrizioneComando},
		{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
    };
    
    /* USE CASE 11 : Effettua Prenotazione */
    private static final String comandiValidiNuovaPrenotazioneConsole[][] = {
    		{ComandoAggiungiBiglietto.codiceComando,ComandoAggiungiBiglietto.descrizioneComando},
    		{ComandoCalcolaTotalePrenotazione.codiceComando,ComandoCalcolaTotalePrenotazione.descrizioneComando},
    		{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
    };
    
    private static final String comandiValidiConfermaPrenotazioneConsole[][] = {
    		{ComandoConfermaPrenotazione.codiceComando,ComandoConfermaPrenotazione.descrizioneComando},
    		{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
    };
	
	
   public static String elencoTuttiComandi(int console){
    	int i=0;
    	StringBuffer elenco = new StringBuffer();
		String comandi[][]=getComandi(console);
		
		
    	for (i=0; i<comandi.length-1; i++) {
			elenco.append(comando(i,console));
			elenco.append("\n");
		}
		elenco.append(comando(i,console));
		return elenco.toString();
    }
	
	private static String comando(int i, int console) {
		String comandi[][]= getComandi(console);
		return " " + comandi[i][0] + ") " + comandi[i][1];
	}

	public static String[][] getComandi(int console){
		
		String comandi[][]=null;
		
		switch (console){
			case HOME: comandi = comandiValidiHomeConsole; break;
			case NUOVA_PRENOTAZIONE: comandi = comandiValidiNuovaPrenotazioneConsole; break;
			case CONFERMA_PRENOTAZIONE: comandi = comandiValidiConfermaPrenotazioneConsole; break;
		};
		return comandi;
	}
	
    public boolean comandoValido(String stringa, int console) {
		String comandi[][]= getComandi(console);
		for(int i = 0; i < comandi.length; i++) {
            if(comandi[i][0].equals(stringa))
                return true;
        }
        return false;
    }
}
