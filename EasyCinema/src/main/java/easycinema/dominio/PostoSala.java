package easycinema.dominio;

public abstract class PostoSala {
	private int numero;

	
	public PostoSala(int numero) {
		this.numero = numero;
	}	
	
	public int getNumero() {
		return numero;
	}
	
	public abstract void verificaCompatibilitąCliente(boolean disabile) throws EccezioneDominio;
	
}
