package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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
	
	// La tariffa della proiezione è funzione della tariffa base, del tipo di proiezione (2d/3d), se il film a cui fa riferimento è un topFilm e dal tipo di sala. 
	private void calcolaTariffa(double tariffa_base) {	
		double maggiorazione_3d = 0;		// tariffa base
		double maggiorazione_topFilm = 0;
		
		if (_3D == true) {
			maggiorazione_3d = tariffa_base * 0.15;		// Una proiezione 3D costa il 15% in più rispetto ad una 2D.
		}
		if (film.isTopFilm() == true) {
			maggiorazione_topFilm = tariffa_base * 0.15;		// Un film Top Film ha una maggiorazione del prezzo del 15%.
		}
		// resta da considerare la tipologia di sala
		tariffa = tariffa_base + maggiorazione_3d + maggiorazione_topFilm;
		tariffa = (double) Math.round(tariffa * 100d) / 100d;		// 2 cifre decimali
	}	
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("- ");
		result.append("Codice: " + codice);
		result.append(", Film: (" + film.getCodice() + ") " + film.getTitolo());
		result.append(", Sala: (" + sala.getNome() + ")");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		result.append(", Data e ora: " + data.format(formatter) + " " + ora.toString());
		result.append(", 3D: " + _3D);
		result.append(", tariffa: " + tariffa);
		return result.toString();
	}

}
