package easycinema.interfaccia.text.uc3_4_7_15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import easycinema.EccezioneDominio;
import easycinema.IEasyCinema;
import easycinema.dominio.Prenotazione;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;
import easycinema.interfaccia.text.uc11.FunzioniComuni;

public class ComandoGetPrenotazioniProiezione extends Comando{

	public static final String codiceComando="4";
	public static final String descrizioneComando="Visualizza le PRENOTAZIONI per una proiezione";

	
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
		System.out.println("   Inserisci il codice della proiezione di interesse: ");
		String codiceProiezione = Parser.getInstance().read();
		try {
			
			List<Prenotazione> prenotazioniProiezione = easyCinema.getPrenotazioniProiezione(codiceProiezione);
			
			if (prenotazioniProiezione.size()!=0) {
				System.out.println("\nElenco prenotazioni per la proiezione " + codiceProiezione + ":");
				for (Prenotazione p : prenotazioniProiezione) {
					System.out.println(p);
				}
			}
			else {
				System.out.println("\nNessuna prenotazione per la proiezione " + codiceProiezione + ".");	
			}
			
			System.out.println("\n   Vuoi conoscere lo stato della sala? (y/n)");
			String scelta = Parser.getInstance().read();
			if (scelta.equals("y")) {
				Map<String, LinkedList<Integer>> statoSalaProiezione;
				
				statoSalaProiezione = easyCinema.getStatoSalaProiezione(codiceProiezione);
				LinkedList<Integer> poltroneDisponibili = statoSalaProiezione.get("Poltrone Disponibili");
				LinkedList<Integer> postazioniDisabiliDisponibili = statoSalaProiezione.get("PostazioniDisabili Disponibili");
				
				LinkedList<Integer> poltroneOccupate = statoSalaProiezione.get("Poltrone Occupate");
				LinkedList<Integer> postazioniDisabiliOccupate = statoSalaProiezione.get("PostazioniDisabili Occupate");
				
				int numPostiDisponibili = poltroneDisponibili.size() + postazioniDisabiliDisponibili.size();			
				int numPostiOccupati = poltroneOccupate.size() + postazioniDisabiliOccupate.size(); 
				int numPostiTotali = numPostiDisponibili + numPostiOccupati;
				
				System.out.println("\n----------------------");
				System.out.println("RIEPILOGO STATO SALA:");
				System.out.println("----------------------");
				System.out.println("Posti totali: " +  numPostiTotali + "\n");
				System.out.println("* Posti disponibili: " +  numPostiDisponibili + " *");
				System.out.println(" - Poltrone (" + poltroneDisponibili.size() + "):");
				FunzioniComuni.stampaPosti(poltroneDisponibili);
				System.out.println(" - Postazioni Disabili (" + postazioniDisabiliDisponibili.size() + "):");
				FunzioniComuni.stampaPosti(postazioniDisabiliDisponibili);
				System.out.println("\n* Posti occupati: " +  numPostiOccupati + " *");
				System.out.println(" - Poltrone (" + poltroneOccupate.size() + "):");
				FunzioniComuni.stampaPosti(poltroneOccupate);
				System.out.println(" - Postazioni Disabili (" + postazioniDisabiliOccupate.size() + "):");
				FunzioniComuni.stampaPosti(postazioniDisabiliOccupate);			
				System.out.println("----------------------");
			}
			else if(!scelta.equals("n")) {
				System.out.println("Scelta non valida!");
			}		
		}
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}
}
