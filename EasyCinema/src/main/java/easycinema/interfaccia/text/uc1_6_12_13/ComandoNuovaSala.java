package easycinema.interfaccia.text.uc1_6_12_13;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovaSala extends Comando {

	public static final String codiceComando="7";
	public static final String descrizioneComando="Aggiungi una nuova SALA";

	
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
			System.out.println("\nTipologia Sala (Basic/Luxe): ");
			String tipologiaSala = Parser.getInstance().read();
			if (!(tipologiaSala.equals("Basic") || tipologiaSala.equals("Luxe")))
				throw new EccezioneDominio("Tipologia di sala non valida!");
			
			System.out.println("Nome: ");
			String nome = Parser.getInstance().read();			
			System.out.println("Numero poltrone: ");
			int numPoltrone = Integer.parseInt(Parser.getInstance().read());			
			System.out.println("Numero postazioni disabili: ");
			int numPostazioniDisabili = Integer.parseInt(Parser.getInstance().read());			
			System.out.println("Tecnologia 2D supportata (true/false): ");
			boolean _2D = Boolean.parseBoolean(Parser.getInstance().read());	
			System.out.println("Tecnologia 3D supportata (true/false): ");
			boolean _3D = Boolean.parseBoolean(Parser.getInstance().read());		
			
			easyCinema.nuovaSala(tipologiaSala, nome, numPoltrone, numPostazioniDisabili, _2D, _3D);
			
			System.out.println("Sala aggiunta con successo!");
		}
		catch(NumberFormatException e) {
			System.out.println("Il numero delle poltrone e delle postazioni disabili deve essere un valore intero!");
		}
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		
	}

}