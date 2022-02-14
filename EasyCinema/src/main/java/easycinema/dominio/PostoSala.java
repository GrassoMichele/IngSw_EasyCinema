package easycinema.dominio;

import easycinema.EccezioneDominio;

public abstract class PostoSala {
	private int numero;

	
	public PostoSala(int numero) {
		this.numero = numero;
	}	
	
	public int getNumero() {
		return numero;
	}
	
	public abstract void verificaCompatibilit‡Cliente(boolean disabile) throws EccezioneDominio;
	
}
