package easycinema.interfaccia.text.uc2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Film;
import easycinema.dominio.Sala;
import easycinema.fabrication.IEasyCinema;
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
	public void esegui(IEasyCinema easyCinema) {
		try {
			System.out.println("Inserisci il codice della nuova proiezione: ");
			String codiceProiezione = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(codiceProiezione, "Codice proiezione");
			
			// visualizzare i film esistenti
			System.out.println("\n*** Elenco dei FILM ***");
			for (Film film: easyCinema.getFilm().values()) {			
				System.out.println("   " + film.toString());
			}
			
			System.out.println("\nInserisci il codice del film: ");
			String codiceFilm = Parser.getInstance().read();
			EccezioneDominio.controlloInserimentoNullo(codiceFilm, "Codice film");

			// visualizzare le sale esistenti
			System.out.println("\n*** Elenco delle SALE ***");
			for (Sala sala: easyCinema.getSale().values()) {			
				System.out.println("   " + sala.toString());
			}		
			System.out.println("\nInserisci il nome della sala: ");
			String nomeSala = Parser.getInstance().read();		
			EccezioneDominio.controlloInserimentoNullo(nomeSala, "Nome sala");
		
		
			System.out.println("\nInserisci la data (dd/mm/yyyy): ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
			//convert String to LocalDate
			LocalDate data = LocalDate.parse(Parser.getInstance().read(), formatter);
			
			System.out.println("\nInserisci l'orario (hh:mm): ");
			formatter = DateTimeFormatter.ofPattern("H:mm");
			LocalTime ora = LocalTime.parse(Parser.getInstance().read(), formatter);
			
			System.out.println("\nE' una proiezione 3D? (true/false): ");
			boolean _3D = Boolean.parseBoolean(Parser.getInstance().read());
			
			System.out.println("\nInserisci la tariffa base: ");
			double tariffaBase = Double.parseDouble(Parser.getInstance().read());
			
			easyCinema.nuovaProiezione(codiceProiezione, codiceFilm, nomeSala, data, ora, _3D, tariffaBase);
			System.out.println("\nPROIEZIONE CREATA CON SUCCESSO!");
		}
		catch (DateTimeParseException e) {
			System.out.println("\nFormato della data o dell'ora non corretto.");
		}
		catch (NumberFormatException e) {
			System.out.println("\nFormato della tariffa base non corretto.");
		} 
		catch (EccezioneDominio e) {
			System.out.println("\n" + e.getMessage());
		}
		
	}

}
