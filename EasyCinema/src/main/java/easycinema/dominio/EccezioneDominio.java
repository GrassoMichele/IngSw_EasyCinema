package easycinema.dominio;

@SuppressWarnings("serial")
public class EccezioneDominio extends Exception {

	public EccezioneDominio(String message) {
		super(message);
	}
	
	
	public static void controlloInserimentoNullo(String inserimento , String soggetto) throws EccezioneDominio {
		if(inserimento.length() == 0) {
			throw new EccezioneDominio("\n" + soggetto + " nullo non ammesso!");
		}
	}
	
}
