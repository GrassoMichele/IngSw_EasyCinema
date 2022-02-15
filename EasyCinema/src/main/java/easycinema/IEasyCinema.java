package easycinema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import easycinema.dominio.Film;
import easycinema.dominio.Prenotazione;
import easycinema.dominio.Proiezione;
import easycinema.dominio.Sala;

public interface IEasyCinema {
	Map<String, Film> getFilm();
	Map<String, Sala> getSale();
	void nuovaProiezione(String codice, String codiceFilm, String nomeSala, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio;
	
	Map<String, Proiezione> getProiezioni();
	List<Prenotazione> getPrenotazioni();
	void nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio;
	Map<String, LinkedList<Integer>> ottieniPostiDisponibili(Proiezione pr);
	void aggiungiBiglietto(int numPosto) throws EccezioneDominio;
	double calcolaTotalePrenotazione() throws EccezioneDominio;
	String confermaPrenotazione() throws EccezioneDominio;
	
	List<Prenotazione> getPrenotazioniProiezione(String codiceProiezione) throws EccezioneDominio;
	Map<String, LinkedList<Integer>> getStatoSalaProiezione(String codiceProiezione) throws EccezioneDominio;
	
	List<Proiezione> getProiezioniPerData(LocalDate data);
	
	String autenticaUtente(String username, String password);	
	
	void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo, boolean disabile) throws EccezioneDominio;
	
	void nuovoFilm(String codice, String titolo, String regia, String cast, int durata, int anno, String trama, String genere, boolean topFilm) throws EccezioneDominio;
	
	void nuovaSala(String tipologiaSala, String nome, int numPoltrone, int numPostazioniDisabili, boolean _2D, boolean _3D) throws EccezioneDominio;
	
	void disdiciPrenotazione(String codice) throws EccezioneDominio;
	
	List<Prenotazione> visualizzaPrenotazioni();
	
}
