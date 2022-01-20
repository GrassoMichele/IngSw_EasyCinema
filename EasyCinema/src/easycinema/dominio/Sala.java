package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

public class Sala {
	private String nome;
	private int numPostiTotali;
	private boolean _2D;
	private boolean _3D;
	private Map<Integer, PostoSala> postiSala;
	
	
	public Sala(String nome, int numPostiTotali, boolean _2D, boolean _3D) {
		this.nome = nome;
		this.numPostiTotali = numPostiTotali;
		this._2D = _2D;
		this._3D = _3D;
		
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

	public boolean is_2D() {
		return _2D;
	}

	public boolean is_3D() {
		return _3D;
	}
		
}
