package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;


public class Proiezione {
	private String codice;
	private Film film;
	private Sala sala;
	private LocalDate data;
	private LocalTime ora;
	private boolean _3D;
	private double tariffa;
	
		
	public Proiezione(String codice, Film film, Sala sala, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) {
		this.codice = codice;
		this.film = film;
		this.sala = sala;
		this.data = data;
		this.ora = ora;
		this._3D = _3D;
		tariffa = tariffaBase;
		calcolaTariffa();
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
	
	// La tariffa della proiezione � funzione della tariffa base, del tipo di proiezione (2d/3d), se il film a cui fa riferimento � un topFilm e dal tipo di sala. 
	private void calcolaTariffa() {	
		if (_3D == true) {
			tariffa *= 1.15;		// Una proiezione 3D costa il 15% in pi� rispetto ad una 2D.
		}
		if (film.isTopFilm() == true) {
			tariffa *= 1.15;		// Un film Top Film ha una maggiorazione del prezzo del 15%.
		}
		
		// resta da considerare la tipologia di sala
	}	

}
