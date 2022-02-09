package easycinema.interfaccia.text.uc11;

import java.util.List;

import easycinema.dominio.IEasyCinema;
import easycinema.dominio.EccezioneDominio;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Console;
import easycinema.interfaccia.text.ElencoComandi;
import easycinema.interfaccia.text.Parser;
import easycinema.interfaccia.text.uc2.ComandoElencoProiezioni;

public class ComandoNuovaPrenotazione extends Comando {
	
	public static final String codiceComando="1";
	public static final String descrizioneComando="Effettua una PRENOTAZIONE";

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
		ComandoElencoProiezioni cep = new ComandoElencoProiezioni();
		cep.esegui(easyCinema);
		System.out.println("\n   Inserisci il codice della proiezione a cui vuoi assistere: ");
		String codiceProiezione = Parser.getInstance().read();
		try {
			easyCinema.nuovaPrenotazione(codiceProiezione);
			
			System.out.println("\nPosti disponibili in sala: ");
			List<Integer> postiDisponibili = easyCinema.ottieniPostiDisponibili();
			FunzioniComuni.stampaPosti(postiDisponibili);
			
			Console nuovaPrenotazioneConsole = new Console(ElencoComandi.NUOVA_PRENOTAZIONE, "NUOVA PRENOTAZIONE");
			nuovaPrenotazioneConsole.start(easyCinema);
			
		} catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}		
	}

}
