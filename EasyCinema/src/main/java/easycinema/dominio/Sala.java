package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

public class Sala {
	private String nome;
	private int numPostiTotali;
	private boolean _2D;
	private boolean _3D;
	private Map<Integer, PostoSala> postiSala;
	
	
	public Sala(String nome, int numPostiTotali, boolean _2D, boolean _3D) throws EccezioneDominio {
		this.nome = nome;
		setNumPostiTotali(numPostiTotali);
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
	
	int getSizePostiSala() {
		return postiSala.size();
	}
	
	
	private void setNumPostiTotali(int numPostiTotali) throws EccezioneDominio {
		if (numPostiTotali > 0) {
			this.numPostiTotali = numPostiTotali;
		}
		else {
			throw new EccezioneDominio("Deve essere presente almeno un posto in sala!");
		}		
		
	}
	
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(" - ");
		result.append("Nome: " + this.nome);
		result.append(", numero posti: " + this.numPostiTotali);
		result.append(", 2D: " + this._2D);
		result.append(", 3D: " + this._3D);
		return result.toString();
	}
		
}
