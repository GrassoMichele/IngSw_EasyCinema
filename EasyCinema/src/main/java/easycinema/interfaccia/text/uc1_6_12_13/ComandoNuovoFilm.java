package easycinema.interfaccia.text.uc1_6_12_13;

import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovoFilm extends Comando {

	public static final String codiceComando="6";
	public static final String descrizioneComando="Aggiungi un nuovo film";

	
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
			System.out.println("\nCodice: ");
			String codice = Parser.getInstance().read();
			if (codice.length()==0)
				throw new EccezioneDominio("Inserisci un codice valido!");
			System.out.println("Titolo: ");
			String titolo = Parser.getInstance().read();
			System.out.println("Regia: ");
			String regia = Parser.getInstance().read();
			System.out.println("Cast: ");
			String cast = Parser.getInstance().read();			
		
			System.out.println("Durata: ");
			int durata = Integer.parseInt(Parser.getInstance().read());
			System.out.println("Anno: ");
			int anno = Integer.parseInt(Parser.getInstance().read());
			
			System.out.println("Trama: ");
			String trama = Parser.getInstance().read();	
			System.out.println("Genere: ");
			String genere = Parser.getInstance().read();	
			System.out.println("Top film: ");
			boolean topFilm = Boolean.parseBoolean(Parser.getInstance().read());		
			
			easyCinema.nuovoFilm(codice, titolo, regia, cast, durata, anno, trama, genere, topFilm);
			
			System.out.println("Film aggiunto con successo!");
		}
		catch(NumberFormatException e) {
			System.out.println("La durata e l'anno devono essere valori interi!");
		}
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		
	}

}
