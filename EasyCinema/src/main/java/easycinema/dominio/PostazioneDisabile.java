package easycinema.dominio;

public class PostazioneDisabile extends PostoSala{

	public PostazioneDisabile(int numero) {
		super(numero);
	}

	@Override
	public void verificaCompatibilitąCliente(boolean disabile) throws EccezioneDominio {
		if (!disabile) 
			throw new EccezioneDominio("Non puoi prenotare un posto riservato ai disabili!");
	}

}
