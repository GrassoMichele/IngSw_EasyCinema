package easycinema.interfaccia.text.uc11;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Console;
import easycinema.interfaccia.text.ElencoComandi;

public class ComandoCalcolaTotalePrenotazione extends Comando {

	public static final String codiceComando="2";
	public static final String descrizioneComando="Procedi con il pagamento";
	
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
		try {
			System.out.println("\nIl totale della prenotazione è: " + easyCinema.calcolaTotalePrenotazione() + "\n");
			Console confermaPrenotazioneConsole = new Console(ElencoComandi.CONFERMA_PRENOTAZIONE, "CONFERMA PRENOTAZIONE");
			confermaPrenotazioneConsole.start(easyCinema);
			exit = true;
		} catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}
}
