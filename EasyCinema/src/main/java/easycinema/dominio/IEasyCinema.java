package easycinema.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface IEasyCinema {
	Map<String, Film> getFilm();
	Map<String, Sala> getSale();
	void nuovaProiezione(String codice, String codiceFilm, String nomeSala, LocalDate data, LocalTime ora, boolean _3D, double tariffaBase) throws EccezioneDominio;
	
	Map<String, Proiezione> getProiezioni();
	List<Prenotazione> getPrenotazioni();
	void nuovaPrenotazione(String codiceProiezione) throws EccezioneDominio;
	List<Integer> ottieniPostiDisponibili();
	void aggiungiBiglietto(int numPosto) throws EccezioneDominio;
	double calcolaTotalePrenotazione() throws EccezioneDominio;
	String confermaPrenotazione() throws EccezioneDominio;
	
	List<Prenotazione> getPrenotazioniProiezione(String codiceProiezione) throws EccezioneDominio;
	Map<String, LinkedList<Integer>> getStatoSalaProiezione(String codiceProiezione) throws EccezioneDominio;
	
	List<Proiezione> getProiezioniPerData(LocalDate data);
	
	String autenticaUtente(String username, String password);	
	
	void nuovoCliente(String codiceFiscale, String nome, String cognome, String indirizzo) throws EccezioneDominio;
	
	
}
