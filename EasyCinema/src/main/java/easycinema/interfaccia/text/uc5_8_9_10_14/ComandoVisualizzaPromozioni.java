package easycinema.interfaccia.text.uc5_8_9_10_14;

import java.util.List;

import easycinema.dominio.Promo;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;

public class ComandoVisualizzaPromozioni extends Comando{

	public static final String codiceComando="6";
	public static final String descrizioneComando="Visualizza le PROMOZIONI";

	
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
		List<Promo> promozioni = easyCinema.visualizzaPromozioni();
		
		if(promozioni.size() != 0) {
			System.out.println("*** Elenco PROMOZIONI ***");
			for (Promo promozione : promozioni)
				System.out.println("   " + promozione);
		}
		else
			System.out.println("Nessuna promozione presente.");
	}

}
