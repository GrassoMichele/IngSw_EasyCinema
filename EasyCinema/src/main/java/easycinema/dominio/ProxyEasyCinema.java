package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProxyEasyCinema implements IEasyCinema{
	private static ProxyEasyCinema istanza;
	private EasyCinema easyCinema;		
	private GestoreUtenti gestoreUtenti;
	
	private ProxyEasyCinema() {
		easyCinema = EasyCinema.getIstanza();
		gestoreUtenti = GestoreUtenti.getIstanza();
	}
	
	public static ProxyEasyCinema getIstanza() {
		if(istanza == null) {
			istanza = new ProxyEasyCinema();
		}
		return istanza;
	}	
		
		
	@Override
	public String autenticaUtente(String username, String password) {
		return gestoreUtenti.autenticaUtente(username, password);
	}
	
	@Override
	public Map<String, Film> getFilm() {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.getFilm();
		return null;
	}

	@Override
	public Map<String, Sala> getSale() {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.getSale();
		return null;
	}

	@Override
	public void nuovaProiezione(String codice, String codiceFilm, String nomeSala, LocalDate data, LocalTime ora,
			boolean _3d, double tariffaBase) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovaProiezione(codice, codiceFilm, nomeSala, data, ora, _3d, tariffaBase);
	}

	@Override
	public Map<String, Proiezione> getProiezioni() {
		if (gestoreUtenti.controlloAutorizzazione(Utente.class))
			return easyCinema.getProiezioni();
		return null;
	}

	@Override
	public List<Prenotazione> getPrenotazioni() {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.getPrenotazioni();
		return null;
	}

	@Override
	public void nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			easyCinema.nuovaPrenotazione(codiceProiezione);		
	}

	@Override
	public List<Integer> ottieniPostiDisponibili() {
		if (gestoreUtenti.controlloAutorizzazione(Utente.class))
			return easyCinema.ottieniPostiDisponibili();
		return null;
	}

	@Override
	public void aggiungiBiglietto(int numPosto) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			easyCinema.aggiungiBiglietto(numPosto);		
	}

	@Override
	public double calcolaTotalePrenotazione() throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			return easyCinema.calcolaTotalePrenotazione();
		return 0;
	}

	@Override
	public String confermaPrenotazione() throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			return easyCinema.confermaPrenotazione();
		return null;
	}

	@Override
	public List<Prenotazione> getPrenotazioniProiezione(String codiceProiezione) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.getPrenotazioniProiezione(codiceProiezione);
		return null;
	}

	@Override
	public Map<String, LinkedList<Integer>> getStatoSalaProiezione(String codiceProiezione) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.getStatoSalaProiezione(codiceProiezione);
		return null;
	}

	@Override
	public List<Proiezione> getProiezioniPerData(LocalDate data) {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			return easyCinema.getProiezioniPerData(data);
		return null;
	}
	
	@Override
	public void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovoCliente(codiceFiscale, nome, cognome, indirizzo);
	}

	@Override
	public void nuovoFilm(String codice, String titolo, String regia, String cast, int durata, int anno, String trama, String genere, boolean topFilm) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovoFilm(codice, titolo, regia, cast, durata, anno, trama, genere, topFilm);
	}
	

}
