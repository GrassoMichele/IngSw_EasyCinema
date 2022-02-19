package easycinema.interfaccia.text.uc3_4_7_15;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovoCliente extends Comando {

	public static final String codiceComando="5";
	public static final String descrizioneComando="Aggiungi un nuovo CLIENTE";

	
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
			System.out.println("\nCodice fiscale: ");
			String codiceFiscale = Parser.getInstance().read();
			System.out.println("Nome: ");
			String nome = Parser.getInstance().read();
			System.out.println("Cognome: ");
			String cognome = Parser.getInstance().read();
			System.out.println("Indirizzo: ");
			String indirizzo = Parser.getInstance().read();

			System.out.println("Disabile (y/altro tasto): ");
			String scelta = Parser.getInstance().read();
			boolean disabile = false;
			if (scelta.equals("y"))
				disabile = true;	

			System.out.println("Sesso (M/F): ");
			char sesso;
			String sessoStringa = Parser.getInstance().read();
			if (sessoStringa.equals("M") || sessoStringa.equals("F"))
				sesso = sessoStringa.charAt(0);
			else {
				throw new EccezioneDominio("Sesso non valido.");
			}

			System.out.println("Anno di nascita: ");
			int annoNascita = Integer.parseInt(Parser.getInstance().read());


			easyCinema.nuovoCliente(codiceFiscale, nome, cognome, indirizzo, disabile, sesso, annoNascita);
			System.out.println("Cliente aggiunto correttamente al sistema.");
		} catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}

}
