package easycinema.dominio;

public class Cliente extends Utente {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	@SuppressWarnings("unused")
	private String indirizzo;
	private double credito;
	private boolean disabile;
	private char sesso;
	private int annoNascita;
	
	
	public Cliente(String codiceFiscale, String nome, String cognome, String indirizzo, boolean disabile, char sesso, int annoNascita) {
		super(codiceFiscale, codiceFiscale);
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.credito = 10;
		this.disabile = disabile;
		this.sesso = sesso;
		this.annoNascita = annoNascita;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public double getCredito() {
		return credito;
	}
	
	public boolean getDisabile() {
		return disabile;
	}

	public void setCredito(double credito) throws EccezioneDominio {
		if (credito >= 0) {
			this.credito = credito;
		}
		else {
			throw new EccezioneDominio("Credito insufficiente!");
		}		
	}

	@Override
	public String getDescrizioneTipologia() {
		return "cliente";
	}
	
	public char getSesso() {
		return sesso;
	}

	public int getAnnoNascita() {
		return annoNascita;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Codice fiscale: " + codiceFiscale);
		result.append(", Nome: " + nome);
		result.append(", Cognome: " + cognome);		
		return result.toString();
	}
	
	
}
