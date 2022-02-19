package easycinema.interfaccia.text.uc11;

import java.util.LinkedList;
import java.util.Map;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoAggiungiBiglietto extends Comando {

	public static final String codiceComando="1";
	public static final String descrizioneComando="Acquista biglietto";
	
	
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
		System.out.println("\nInserisci il numero del posto da prenotare: ");
		try {
			int numPosto = Integer.parseInt(Parser.getInstance().read());
			easyCinema.aggiungiBiglietto(numPosto);
			System.out.println("Il posto " + numPosto + " è stato riservato con successo");
		}
		catch (NumberFormatException e) {
			System.out.println("Inserisci un numero intero!");
		}
		catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		finally {
			System.out.println("\nPosti disponibili in sala: ");
			Map<String, LinkedList<Integer>> postiDisponibili = easyCinema.ottieniPostiDisponibili(null);
			System.out.println("- Poltrone: ");
			FunzioniComuni.stampaPosti(postiDisponibili.get("Poltrone Disponibili"));
			System.out.println("- PostazioniDisabili: ");
			FunzioniComuni.stampaPosti(postiDisponibili.get("PostazioniDisabili Disponibili"));
		}
				
	}

}
