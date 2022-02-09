package easycinema.dominio;

import java.util.ArrayList;
import java.util.List;

public class GestoreUtenti {
	private static GestoreUtenti istanza;
	private List<Utente> utenti;
	private Utente utenteCorrente;
	
	
	private GestoreUtenti() {
		utenti = new ArrayList<Utente>();		
		caricaUtenti();
	}
	
	public static GestoreUtenti getIstanza() {
		if(istanza == null) {
			istanza = new GestoreUtenti();
		}
		return istanza;
	}	
	
	
	public String autenticaUtente(String username, String password) {
		String risultatoAutenticazione = "Fallita";
		setUtenteCorrente(null);
		
		for (Utente u : utenti) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
				setUtenteCorrente(u);
				risultatoAutenticazione = u.getDescrizioneTipologia();
				break;
			}	
		}
		return risultatoAutenticazione;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean controlloAutorizzazione(Class categoriaUtenteAbilitata) {
		if (utenteCorrente!= null && categoriaUtenteAbilitata.isInstance(utenteCorrente)) {
			return true;
		}
		return false;
	}
	
	
	void modificaCreditoCliente(double importo) throws EccezioneDominio {
		if (utenteCorrente instanceof Cliente) {
			Cliente c = (Cliente) utenteCorrente;
			double credito = c.getCredito();		
			c.setCredito(credito + importo);
		}		
	}	
		
	public void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo) throws EccezioneDominio {
		boolean trovato = false;
		for (Utente u : utenti) {
			if (u instanceof Cliente && ((Cliente) u).getCodiceFiscale().equals(codiceFiscale))
				trovato = true;
		}
		if (!trovato) {
			Cliente c = new Cliente(codiceFiscale,nome,cognome,indirizzo);	
			utenti.add(c);			
		}			
		else 
			throw new EccezioneDominio("Il codice fiscale inserito appartiene già ad un utente!");
	}
	
	private void setUtenteCorrente(Utente u) {
		utenteCorrente = u;
	}
	
	Cliente getClienteCorrente() {
		return (Cliente) utenteCorrente;
	}
		
	private void caricaUtenti() {
		utenti.add(new Titolare("admin","admin"));
		utenti.add(new Cliente("cliente","Mario","Rossi", "via Doria"));
	}

}
