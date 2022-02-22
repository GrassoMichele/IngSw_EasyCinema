package easycinema.interfaccia.text.uc1_6_12_13;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovoFilm extends Comando {

	public static final String codiceComando="11";
	public static final String descrizioneComando="Aggiungi un nuovo FILM";

	
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
			System.out.println("Codice film: ");
			String codice = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(codice, "Codice");
			
			System.out.println("Titolo: ");
			String titolo = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(titolo, "Titolo");
			
			System.out.println("Regia: ");
			String regia = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(regia, "Regia");
			
			System.out.println("Cast: ");
			String cast = Parser.getInstance().read();		
			EccezioneDominio.controlloInserimentoNullo(cast, "Cast");
		
			System.out.println("Durata: ");
			int durata = Integer.parseInt(Parser.getInstance().read());
			
			System.out.println("Anno: ");
			int anno = Integer.parseInt(Parser.getInstance().read());
			
			System.out.println("Trama: ");
			String trama = Parser.getInstance().read();	
			EccezioneDominio.controlloInserimentoNullo(trama, "Trama");
			
			System.out.println("Genere: ");
			String genere = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(genere, "Genere");
			
			System.out.println("Top film (true/false): ");
			boolean topFilm = Boolean.parseBoolean(Parser.getInstance().read());		
			
			easyCinema.nuovoFilm(codice, titolo, regia, cast, durata, anno, trama, genere, topFilm);
			
			System.out.println("\nFILM AGGIUNTO CON SUCCESSO!");
		}
		catch(NumberFormatException e) {
			System.out.println("\nLa durata e l'anno devono essere valori interi!");
		}
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		
	}

}
