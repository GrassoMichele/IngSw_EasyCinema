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
	
	// La tariffa della proiezione è funzione della tariffa base, del tipo di proiezione (2d/3d), se il film a cui fa riferimento è un topFilm e dal tipo di sala. 
	public void calcolaTariffa() {		
		tariffa *=1;
	}
	
	
	
	
	

}
