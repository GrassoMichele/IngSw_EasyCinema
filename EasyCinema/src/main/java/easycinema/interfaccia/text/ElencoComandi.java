package easycinema.interfaccia.text;

import easycinema.interfaccia.text.uc11.ComandoAggiungiBiglietto;
import easycinema.interfaccia.text.uc11.ComandoCalcolaTotalePrenotazione;
import easycinema.interfaccia.text.uc11.ComandoConfermaPrenotazione;
import easycinema.interfaccia.text.uc11.ComandoElencoPrenotazioni;
import easycinema.interfaccia.text.uc11.ComandoNuovaPrenotazione;
import easycinema.interfaccia.text.uc1_6_12_13.ComandoDisdiciPrenotazione;
import easycinema.interfaccia.text.uc1_6_12_13.ComandoNuovaSala;
import easycinema.interfaccia.text.uc1_6_12_13.ComandoNuovoFilm;
import easycinema.interfaccia.text.uc1_6_12_13.ComandoVisualizzaPrenotazioni;
import easycinema.interfaccia.text.uc2.ComandoElencoProiezioni;
import easycinema.interfaccia.text.uc2.ComandoNuovaProiezione;
import easycinema.interfaccia.text.uc3_4_7_15.ComandoAutenticaUtente;
import easycinema.interfaccia.text.uc3_4_7_15.ComandoGetPrenotazioniProiezione;
import easycinema.interfaccia.text.uc3_4_7_15.ComandoGetProiezioniPerData;
import easycinema.interfaccia.text.uc3_4_7_15.ComandoNuovoCliente;
import easycinema.interfaccia.text.uc5_8_9_10_14.ComandoNuovaPromozione;
import easycinema.interfaccia.text.uc5_8_9_10_14.ComandoRicaricaCreditoCliente;
import easycinema.interfaccia.text.uc5_8_9_10_14.ComandoVisualizzaProfilo;
import easycinema.interfaccia.text.uc5_8_9_10_14.ComandoVisualizzaProiezioniSale;
import easycinema.interfaccia.text.uc5_8_9_10_14.ComandoVisualizzaPromozioni;

public class ElencoComandi {
	
	public static final int LOGIN = 0;
	public static final int HOME_TITOLARE = 1;
	public static final int HOME_CLIENTE = 2;
	/*UC 11*/
	public static final int NUOVA_PRENOTAZIONE = 3;
	public static final int CONFERMA_PRENOTAZIONE = 4;
	
	/* LOGIN */
	private static final String comandiValidiLogin[][] = {
			{ComandoAutenticaUtente.codiceComando,ComandoAutenticaUtente.descrizioneComando},
			{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
	    };
	
	/* MENU' PRINCIPALE */
	private static final String comandiValidiHomeTitolareConsole[][] = {
			{ComandoNuovaProiezione.codiceComando,ComandoNuovaProiezione.descrizioneComando},
			{ComandoElencoProiezioni.codiceComando,ComandoElencoProiezioni.descrizioneComando},
			{ComandoElencoPrenotazioni.codiceComando,ComandoElencoPrenotazioni.descrizioneComando},
			{ComandoGetPrenotazioniProiezione.codiceComando,ComandoGetPrenotazioniProiezione.descrizioneComando},
			{ComandoNuovoCliente.codiceComando,ComandoNuovoCliente.descrizioneComando},
			{ComandoNuovoFilm.codiceComando,ComandoNuovoFilm.descrizioneComando},
			{ComandoNuovaSala.codiceComando,ComandoNuovaSala.descrizioneComando},
			{ComandoVisualizzaProiezioniSale.codiceComando,ComandoVisualizzaProiezioniSale.descrizioneComando},
			{ComandoRicaricaCreditoCliente.codiceComando,ComandoRicaricaCreditoCliente.descrizioneComando},
			{ComandoNuovaPromozione.codiceComando,ComandoNuovaPromozione.descrizioneComando},
			{ComandoVisualizzaProfilo.codiceComando,ComandoVisualizzaProfilo.descrizioneComando},
			{ComandoVisualizzaPromozioni.codiceComando,ComandoVisualizzaPromozioni.descrizioneComando},
			{ComandoEsci.codiceComando, ComandoEsci.descrizioneComando}
	};

	private static final String comandiValidiHomeClienteConsole[][] = {
			{ComandoNuovaPrenotazione.codiceComando,ComandoNuovaPrenotazione.descrizioneComando},
			{ComandoElencoProiezioni.codiceComando,ComandoElencoProiezioni.descrizioneComando},
			{ComandoGetProiezioniPerData.codiceComando,ComandoGetProiezioniPerData.descrizioneComando},
			{ComandoDisdiciPrenotazione.codiceComando,ComandoDisdiciPrenotazione.descrizioneComando},
			{ComandoVisualizzaPrenotazioni.codiceComando,ComandoVisualizzaPrenotazioni.descrizioneComando},
			{ComandoVisualizzaProfilo.codiceComando,ComandoVisualizzaProfilo.descrizioneComando},
			{ComandoVisualizzaPromozioni.codiceComando,ComandoVisualizzaPromozioni.descrizioneComando},
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
			case LOGIN: comandi = comandiValidiLogin; break;
			case HOME_TITOLARE: comandi = comandiValidiHomeTitolareConsole; break;
			case HOME_CLIENTE: comandi = comandiValidiHomeClienteConsole; break;
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
