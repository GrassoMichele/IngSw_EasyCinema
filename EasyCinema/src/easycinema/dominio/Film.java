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
	
	
}
