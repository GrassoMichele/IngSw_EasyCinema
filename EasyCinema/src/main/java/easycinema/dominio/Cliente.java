package easycinema.dominio;

public class Cliente extends Utente {
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String indirizzo;
	private double credito;
	
	public Cliente(String codiceFiscale, String nome, String cognome, String indirizzo) {
		super(codiceFiscale, codiceFiscale);
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.indirizzo = indirizzo;
		this.credito = 10;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public double getCredito() {
		return credito;
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
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Codice fiscale: " + codiceFiscale);
		result.append(", Nome: " + nome);
		result.append(", Cognome: " + cognome);		
		return result.toString();
	}
	
	
}
