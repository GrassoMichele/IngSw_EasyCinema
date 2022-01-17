package easycinema.dominio;

public class Biglietto {
	private String id;
	private PostoSala postoSala;
	
	
	public Biglietto(String id, PostoSala postoSala) {
		this.id = id;
		this.postoSala = postoSala;
	}
	
	public int otteniPostoSala() {
		return postoSala.getNumero();
	}
	
	
	
}
