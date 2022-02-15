package easycinema.dominio;

public class Biglietto {
	private String id;
	private PostoSala postoSala;
	
	
	public Biglietto(String id, PostoSala postoSala) {
		this.id = id;
		this.postoSala = postoSala;
	}
	
	public int otteniNumeroPostoSala() {
		return postoSala.getNumero();
	}
	
	@SuppressWarnings("rawtypes")
	public int otteniNumeroPostoSala(Class tipologiaPosto) {
		if (postoSala.getClass().equals(tipologiaPosto))
			return postoSala.getNumero();
		else
			return 0;
		
	}
	
	public String getId() {
		return id;
	}
	
}
