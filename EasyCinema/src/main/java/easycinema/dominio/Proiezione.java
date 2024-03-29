package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;


public class Proiezione {
	private String codice;
	private Film film;
	private Sala sala;
	private LocalDate data;
	private LocalTime ora;
	private boolean _3D;
	private double tariffa;
	
		
	public Proiezione(String codice, Film film, Sala sala, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio {
		this.codice = codice;
		this.film = film;
		this.sala = sala;
		this.data = data;
		this.ora = ora;
		this._3D = _3D;
		
		setTariffa(tariffaBase);		
	}
	
	private void setTariffa(double tariffaBase) throws EccezioneDominio {
		if (tariffaBase > 0) {
			calcolaTariffa(tariffaBase);	
		}
		else {
			throw new EccezioneDominio("La tariffa deve essere maggiore di 0.");
		}
	}

	public int getNumPostiSala() {
		return sala.getNumPostiTotali(); 
	}
	
	public String getNomeSala() {
		return sala.getNome();
	}

	public double getTariffa() {
		return tariffa;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public LocalDate getData() {
		return data;
	}

	public LocalTime getOra() {
		return ora;
	}
	
	public Sala getSala() {
		return sala;
	}
	
	public int getDurataFilm() {
		return film.getDurata();
	}
	
	public boolean isTopFilm() {
		return film.isTopFilm();
	}
	
	
	// La tariffa della proiezione � funzione della tariffa base, del tipo di proiezione (2d/3d), se il film a cui fa riferimento � un topFilm e dal tipo di sala. 
	// Ciascuna maggiorazione viene calcolata a partire dalla tariffa base.
	private void calcolaTariffa(double tariffa_base) {	
		double maggiorazione_3d = 0;		// tariffa base
		double maggiorazione_topFilm = 0;
		double maggiorazione_tipologiaSala = 0;
		
		if (_3D == true) {
			maggiorazione_3d = tariffa_base * 0.15;		// Una proiezione 3D costa il 15% in pi� rispetto ad una 2D.
		}
		if (film.isTopFilm() == true) {
			maggiorazione_topFilm = tariffa_base * 0.15;		// Un film Top Film ha una maggiorazione del prezzo del 15%.
		}
		
		maggiorazione_tipologiaSala = sala.getMaggiorazioneTariffa();	// Ciascuna tipologia di sala offre servizi diversi a prezzi diversi.
		
		tariffa = tariffa_base + maggiorazione_3d + maggiorazione_topFilm + maggiorazione_tipologiaSala;
		tariffa = (double) Math.round(tariffa * 100d) / 100d;		// 2 cifre decimali
	}	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- ");
		result.append("Codice: " + codice);
		result.append(", Film " + film.getCodice() + ": " + film.getTitolo());
		if(film.isTopFilm())
			result.append(" (top Film)");
		result.append(", Sala: (" + sala.getNome() + " - " + sala.getTipologiaSala() + ")");
		DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
		result.append(", Data e ora: " + data.format(dataFormatter) + " " + ora.toString());
		result.append(", 3D: " + _3D);
		result.append(", tariffa: " + tariffa);
		return result.toString();
	}

}
