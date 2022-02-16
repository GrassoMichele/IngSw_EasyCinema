package easycinema;

import easycinema.dominio.Sala;
import easycinema.dominio.SalaBasic;
import easycinema.dominio.SalaLuxe;

// Simple Factory Pattern (non è un creational design pattern, non rientra tra i pattern GoF). 
public class SaleFactory {
	public static Sala creaSala(String tipologia, String nome, int numPoltrone, int numPostazioniDisabili, boolean _2D, boolean _3D) throws EccezioneDominio {
		if (numPoltrone<0 || numPostazioniDisabili<0)
			throw new EccezioneDominio("Non è possibile avere un numero negativo di poltrone o postazioni disabili.");
		if(!_2D && !_3D)
			throw new EccezioneDominio("ERRORE: La sala deve supportare almeno una tra le due tecnologie: 2D, 3D.");
		
		switch (tipologia) {
			case "Basic": return new SalaBasic(nome, numPoltrone, numPostazioniDisabili, _2D, _3D);
			case "Luxe": return new SalaLuxe(nome, numPoltrone, numPostazioniDisabili, _2D, _3D);				
			default:
				throw new EccezioneDominio("Tipologia di sala non esistente!");				
		}
	}
}