package easycinema.interfaccia.text.uc5_8_9_10_14;

import easycinema.dominio.EccezioneDominio;
import easycinema.fabrication.IEasyCinema;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoRicaricaCreditoCliente extends Comando{

	public static final String codiceComando="9";
	public static final String descrizioneComando="Ricarica credito CLIENTE";

	
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
			System.out.println("Codice fiscale del cliente: ");
			String codiceFiscale = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(codiceFiscale, "Codice fiscale");
			
			System.out.println("Importo della ricarica: ");
			double importo = Double.parseDouble(Parser.getInstance().read());
			
			System.out.println();
			
			if(importo > 0) {
				easyCinema.ricaricaCreditoCliente(codiceFiscale, importo);				
				System.out.println("RICARICA DEL CONTO EFFETTUATA!");
			}
			else
				throw new EccezioneDominio("ERRORE: L'importo della ricarica deve essere un valore positivo.");
		}
		catch (NumberFormatException e) {
			System.out.println("ERRORE: Formato dell'importo ricarica non corretto.");
		} 
		catch(EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
	}

}
