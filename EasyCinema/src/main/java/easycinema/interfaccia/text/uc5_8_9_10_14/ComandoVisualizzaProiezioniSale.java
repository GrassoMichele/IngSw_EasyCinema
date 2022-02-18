package easycinema.interfaccia.text.uc5_8_9_10_14;

import java.util.Map;

import easycinema.IEasyCinema;
import easycinema.dominio.Proiezione;
import easycinema.dominio.Sala;
import easycinema.interfaccia.text.Comando;

public class ComandoVisualizzaProiezioniSale extends Comando{

	public static final String codiceComando="8";
	public static final String descrizioneComando="Visualizza le PROIEZIONI in sala";

	
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
		Map<String, Sala> sale = easyCinema.getSale();
		Map<String, Proiezione> proiezioniSale = easyCinema.visualizzaProiezioniSale();
		
		System.out.println("\nProiezioni in corso (o prossime) in sala: ");
		for (String nomeSala : sale.keySet()) {
			System.out.println("-------------------------------------------");
			System.out.println("Sala: " + sale.get(nomeSala));
			Proiezione proiezione = proiezioniSale.get(nomeSala);
			String descrizioneProiezione = (proiezione != null) ? proiezione.toString() : "Nessuna proiezione in corso o futura"; 
			System.out.println("Proiezione: " + descrizioneProiezione);
			System.out.println("-------------------------------------------");
		}		
	}

}
