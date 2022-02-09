package easycinema.interfaccia.text.uc3_4_7_15;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import easycinema.dominio.IEasyCinema;
import easycinema.dominio.Proiezione;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoGetProiezioniPerData extends Comando{

	public static final String codiceComando="3";
	public static final String descrizioneComando="Visualizza la programmazione per una data";

	
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
			System.out.println("   Inserisci la data (dd/mm/yyyy): ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			String dataS = Parser.getInstance().read();
			LocalDate data = LocalDate.parse(dataS, formatter);
			
			List<Proiezione> proiezioni = easyCinema.getProiezioniPerData(data);
			if (proiezioni.size()!=0) {
				System.out.println("   Elenco:");
				for (Proiezione pr : proiezioni) {
					System.out.println(pr);
				}
			}
			else {
				System.out.println("Nessuna proiezione in programma per la data " + dataS + ".");
			}
			
		}
		catch (DateTimeParseException e) {
			System.out.println("Formato della data o dell'ora non corretto.");
		}
		
	}

}
