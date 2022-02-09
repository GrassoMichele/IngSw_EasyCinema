package easycinema.interfaccia.text.uc3_4_7_15;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import easycinema.dominio.IEasyCinema;
import easycinema.dominio.EccezioneDominio;
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
				LinkedList<Integer> postiDisponibili = statoSalaProiezione.get("Disponibili");
				LinkedList<Integer> postiOccupati = statoSalaProiezione.get("Occupati");
				
				int postiTotali = postiDisponibili.size() + postiOccupati.size(); 
				System.out.println("   Posti totali: " +  postiTotali + "\n");
				System.out.println("   Posti disponibili: " +  postiDisponibili.size());
				FunzioniComuni.stampaPosti(postiDisponibili);
				System.out.println("   Posti occupati: " +  postiOccupati.size());
				FunzioniComuni.stampaPosti(postiOccupati);							
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
