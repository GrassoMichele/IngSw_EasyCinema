package easycinema.interfaccia.text.uc3_4_7_15;

import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovoCliente extends Comando {

	public static final String codiceComando="5";
	public static final String descrizioneComando="Aggiungi un nuovo cliente";

	
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
		System.out.println("\nCodice fiscale: ");
		String codiceFiscale = Parser.getInstance().read();
		System.out.println("Nome: ");
		String nome = Parser.getInstance().read();
		System.out.println("Cognome: ");
		String cognome = Parser.getInstance().read();
		System.out.println("Indirizzo: ");
		String indirizzo = Parser.getInstance().read();
		
		try {
			easyCinema.nuovoCliente(codiceFiscale, nome, cognome, indirizzo);
			System.out.println("Cliente aggiunto correttamente al sistema.");
		} catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}

}
