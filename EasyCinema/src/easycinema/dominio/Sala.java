package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

public class Sala {
	private String nome;
	private int numPostiTotali;
	private boolean _2D;
	private boolean _3D;
	private Map<Integer, PostoSala> postiSala;
	
	
	public Sala(String nome, int numPostiTotali, boolean _2d, boolean _3d) {
		this.nome = nome;
		this.numPostiTotali = numPostiTotali;
		_2D = _2d;
		_3D = _3d;
		
		this.postiSala = new HashMap<Integer, PostoSala>();
		creaPostiSala();
	}
	
	private void creaPostiSala() {
		PostoSala postoSala; 
		for (int i = 0; i < numPostiTotali; i++) {
			postoSala = new PostoSala(i+1);
			postiSala.put(postoSala.getNumero(), postoSala);			
		}
	}

	public String getNome() {
		return nome;
	}

	public int getNumPostiTotali() {
		return numPostiTotali;
	}
	
	public PostoSala getPostoSala(int numPosto) {
		return postiSala.get(numPosto);
	}
		
}
