package easycinema.dominio;

public class Cliente extends Utente {
	private double credito;
	
	public Cliente(double credito) {
		this.credito = credito;
	}
	
	public double getCredito() {
		return credito;
	}

	public void setCredito(double credito) throws EccezioneDominio {
		if (credito < 0) {
			throw new EccezioneDominio("Credito non sufficiente!");
		}
		else {
			this.credito = credito;
		}		
	}
	
	
}
