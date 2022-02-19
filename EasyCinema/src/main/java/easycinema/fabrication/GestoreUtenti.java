package easycinema.fabrication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import easycinema.dominio.Cliente;
import easycinema.dominio.EccezioneDominio;
import easycinema.dominio.Titolare;
import easycinema.dominio.Utente;

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
	
	// identificazione utente: accerto l'identità
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
	
	// autenticazione utente: accerto i permessi
	@SuppressWarnings("rawtypes")
	public boolean controlloAutorizzazione(Class categoriaUtenteAbilitata) {
		if (utenteCorrente!= null && categoriaUtenteAbilitata.isInstance(utenteCorrente)) {
			return true;
		}
		return false;
	}
	
	
	public void modificaCreditoCliente(double importo) throws EccezioneDominio {
		if (utenteCorrente instanceof Cliente) {
			Cliente c = (Cliente) utenteCorrente;
			double credito = c.getCredito();		
			c.setCredito(credito + importo);
		}		
	}	
	
	public void modificaCreditoCliente(String codiceFiscale, double importo) throws EccezioneDominio {
		Cliente c = null;		
		for(Utente u : utenti) {
			if (u instanceof Cliente) {
				if (((Cliente) u).getCodiceFiscale().equals(codiceFiscale)) {
					c = (Cliente) u;
					break;
				}					
			}			
		}		
		if (c != null) {
			double credito = c.getCredito();		
			c.setCredito(credito + importo);
		}
		else {
			throw new EccezioneDominio("ERRORE: Il codice fiscale non corrisponde ad alcun cliente.");
		}
	}
		
	public void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo, boolean disabile, char sesso, int annoNascita) throws EccezioneDominio {
		boolean trovato = false;
		for (Utente u : utenti) {
			if (u instanceof Cliente && ((Cliente) u).getCodiceFiscale().equals(codiceFiscale))
				trovato = true;
		}
		if (!trovato) {
			if (sesso == 'M' || sesso == 'F') {
				if((annoNascita > 1800) && (annoNascita < (LocalDate.now().getYear() + 1))) {
					Cliente c = new Cliente(codiceFiscale,nome,cognome,indirizzo, disabile, sesso, annoNascita);	
					utenti.add(c);	
				}
				else {
					throw new EccezioneDominio("Anno di nascita non valido!");
				}
			}
			else {
				throw new EccezioneDominio("Sesso non valido: inserisci M o F!");
			}					
		}			
		else 
			throw new EccezioneDominio("Il codice fiscale inserito appartiene già ad un utente!");
	}
	
	private void setUtenteCorrente(Utente u) {
		utenteCorrente = u;
	}
	
	public Cliente getClienteCorrente() {
		return (Cliente) utenteCorrente;
	}
		
	public int getNumeroUtenti() {
		return utenti.size();
	}
	
	private void caricaUtenti() {
		utenti.add(new Titolare("admin","admin"));
		utenti.add(new Cliente("cliente","Mario","Rossi", "via Doria", false, 'M', 1996));
	}

}
