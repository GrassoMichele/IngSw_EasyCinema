package easycinema.dominio;

public class Poltrona extends PostoSala{

	public Poltrona(int numero) {
		super(numero);
	}

	@Override
	public void verificaCompatibilit�Cliente(boolean disabile) {
		// un cliente disabile pu� prenotare anche posti per poltrone (ad es. per familiari/amici/accompagnatore).
	}

}
