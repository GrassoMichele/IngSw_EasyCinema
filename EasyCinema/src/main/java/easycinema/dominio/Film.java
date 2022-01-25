package easycinema.dominio;

public class Film {
	private String codice;
	private String titolo;
	private String regia;
	private String cast;
	private int durata;
	private int anno;
	private String trama;
	private String genere;
	private boolean topFilm;	
	

	public Film(String codice, String titolo, String regia, String cast, int durata, int anno, String trama,
			String genere, boolean topFilm) {
		this.codice = codice;
		this.titolo = titolo;
		this.regia = regia;
		this.cast = cast;
		this.durata = durata;
		this.anno = anno;
		this.trama = trama;
		this.genere = genere;
		this.topFilm = topFilm;
	}	
	
	public String getCodice() {
		return codice;
	}

	public int getDurata() {
		return durata;
	}

	public boolean isTopFilm() {
		return topFilm;
	}
	
	public String getTitolo() {
		return titolo;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(" - ");
		result.append("Codice: " + codice);
		result.append(", Titolo: " + titolo);
		result.append(", Regia: " + regia);
		result.append(", Cast: " + cast);
		result.append(", Durata: " + durata);
		result.append(", Anno: " + anno);
		result.append(", Trama: " + trama);
		result.append(", Genere: " + genere);
		result.append(", Top Film: " + topFilm);
		return result.toString();
	}	
	
	
	
}
