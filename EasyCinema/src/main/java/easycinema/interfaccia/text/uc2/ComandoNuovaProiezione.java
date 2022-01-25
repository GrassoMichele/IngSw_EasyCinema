package easycinema.interfaccia.text.uc2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import easycinema.dominio.EasyCinema;
import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Film;
import easycinema.dominio.Sala;
import easycinema.interfaccia.text.Comando;
import easycinema.interfaccia.text.Parser;

public class ComandoNuovaProiezione extends Comando {
	
	public static final String codiceComando="1";
	public static final String descrizioneComando="Aggiungi una PROIEZIONE";

	@Override
	public String getCodiceComando() {
		return codiceComando;
	}

	@Override
	public String getDescrizioneComando() {
		return descrizioneComando;
	}

	@Override
	public void esegui(EasyCinema easyCinema) {
		System.out.println("   Inserisci il codice della nuova proiezione: ");
		String codiceProiezione = Parser.getInstance().read();
		
		// visualizzare i film esistenti
		System.out.println("   Elenco dei film:");
		for (Film film: easyCinema.getFilm().values()) {			
			System.out.println("   " + film.toString());
		}
		System.out.println("   Inserisci il codice del film: ");
		String codiceFilm = Parser.getInstance().read();
		
		// visualizzare le sale esistenti
		System.out.println("   Elenco delle sale:");
		for (Sala sala: easyCinema.getSale().values()) {			
			System.out.println("   " + sala.toString());
		}		
		System.out.println("   Inserisci il nome della sala: ");
		String nomeSala = Parser.getInstance().read();		
		
		try {
			System.out.println("   Inserisci la data (dd/mm/yyyy): ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			//convert String to LocalDate
			LocalDate data = LocalDate.parse(Parser.getInstance().read(), formatter);
			
			System.out.println("   Inserisci l'orario (hh:mm): ");
			formatter = DateTimeFormatter.ofPattern("H:mm");
			LocalTime ora = LocalTime.parse(Parser.getInstance().read(), formatter);
			
			System.out.println("   E' una proiezione 3D? (true/false): ");
			boolean _3D = Boolean.parseBoolean(Parser.getInstance().read());
			
			System.out.println("   Inserisci la tariffa base: ");
			double tariffaBase = Double.parseDouble(Parser.getInstance().read());
			
			easyCinema.nuovaProiezione(codiceProiezione, codiceFilm, nomeSala, data, ora, _3D, tariffaBase);
		}
		catch (DateTimeParseException e) {
			System.out.println("Formato della data o dell'ora non corretto.");
		}
		catch (NumberFormatException e) {
			System.out.println("Formato della tariffa base non corretto.");
		} 
		catch (EccezioneDominio e) {
			System.out.println(e.getMessage());
		}
		
	}

}
