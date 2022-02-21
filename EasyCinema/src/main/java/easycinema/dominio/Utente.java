package easycinema.dominio;

public abstract class Utente {
	private String username;
	private String password;
	
	public Utente(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public abstract String getDescrizioneTipologia();

	@Override
	public String toString() {
		return "Username: " + username + ", password: " + password;
	}
	
}
