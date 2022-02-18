package easycinema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import easycinema.dominio.Cliente;
import easycinema.dominio.EasyCinema;
import easycinema.dominio.Film;
import easycinema.dominio.Prenotazione;
import easycinema.dominio.Proiezione;
import easycinema.dominio.Sala;
import easycinema.dominio.Titolare;
import easycinema.dominio.Utente;

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
	public Map<String, LinkedList<Integer>> ottieniPostiDisponibili(Proiezione pr) {
		if (gestoreUtenti.controlloAutorizzazione(Utente.class))
			return easyCinema.ottieniPostiDisponibili(null);		// voglio fare riferimento alla proiezione associata alla prenotazione corrente
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
	public void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo, boolean disabile) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovoCliente(codiceFiscale, nome, cognome, indirizzo, disabile);
	}

	@Override
	public void nuovoFilm(String codice, String titolo, String regia, String cast, int durata, int anno, String trama, String genere, boolean topFilm) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovoFilm(codice, titolo, regia, cast, durata, anno, trama, genere, topFilm);
	}

	@Override
	public void nuovaSala(String tipologiaSala, String nome, int numPoltrone, int numPostazioniDisabili, boolean _2d,
			boolean _3d) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.nuovaSala(tipologiaSala, nome, numPoltrone, numPostazioniDisabili, _2d, _3d);
	}

	@Override
	public void disdiciPrenotazione(String codice) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			easyCinema.disdiciPrenotazione(codice);
	}

	@Override
	public List<Prenotazione> visualizzaPrenotazioni() {
		if (gestoreUtenti.controlloAutorizzazione(Cliente.class))
			return easyCinema.visualizzaPrenotazioni();
		return null;
	}

	@Override
	public Map<String, Proiezione> visualizzaProiezioniSale() {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			return easyCinema.visualizzaProiezioniSale();
		return null;
	}

	@Override
	public void ricaricaCreditoCliente(String codiceFiscale, double importo) throws EccezioneDominio {
		if (gestoreUtenti.controlloAutorizzazione(Titolare.class))
			easyCinema.ricaricaCreditoCliente(codiceFiscale, importo);
		
	}	

}
