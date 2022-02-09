package easycinema.dominio;

public class Titolare extends Utente{

	public Titolare(String username, String password) {
		super(username, password);
	}

	@Override
	public String getDescrizioneTipologia() {
		return "titolare";
	}
}
