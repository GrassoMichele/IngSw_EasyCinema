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
			String genere, boolean topFilm) throws EccezioneDominio {
		this.codice = codice;
		this.titolo = titolo;
		this.regia = regia;
		this.cast = cast;
		setDurata(durata);
		setAnno(anno);
		this.trama = trama;
		this.genere = genere;
		this.topFilm = topFilm;
	}	
	
	private void setDurata(int durata) throws EccezioneDominio {
		if(durata > 0)
			this.durata = durata;
		else
			throw new EccezioneDominio("La durata deve avere valore positivo!");
		
	}
	
	private void setAnno(int anno) throws EccezioneDominio {
		if (anno >= 1895)
			this.anno = anno;
		else
			throw new EccezioneDominio("L'anno deve essere superiore al 1895!");
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
