package easycinema.dominio;

public class Poltrona extends PostoSala{

	public Poltrona(int numero) {
		super(numero);
	}

	@Override
	public void verificaCompatibilitàCliente(boolean disabile) {
		// un cliente disabile può prenotare anche posti per poltrone (ad es. per familiari/amici/accompagnatore).
	}

}
