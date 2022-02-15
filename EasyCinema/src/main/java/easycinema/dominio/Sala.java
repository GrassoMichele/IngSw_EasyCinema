package easycinema.dominio;

import java.util.HashMap;
import java.util.Map;

import easycinema.EccezioneDominio;

public abstract class Sala {
	private String nome;
	private int numPostiTotali;
	private int numPoltrone;
	private int numPostazioniDisabili;
	private boolean _2D;
	private boolean _3D;
	private Map<Integer, PostoSala> postiSala;
	
	
	public Sala(String nome, int numPoltrone, int numPostazioniDisabili, boolean _2D, boolean _3D) throws EccezioneDominio {
		this.nome = nome;
		this.numPoltrone = numPoltrone;
		this.numPostazioniDisabili = numPostazioniDisabili;
		setNumPostiTotali(this.numPoltrone + this.numPostazioniDisabili);
		this._2D = _2D;
		this._3D = _3D;
		
		this.postiSala = new HashMap<Integer, PostoSala>();
		creaPostiSala();
	}
	
	protected abstract String getTipologiaSala();
	protected abstract String getDescrizioneServizi();
	protected abstract double getMaggiorazioneTariffa();
	
	private void creaPostiSala() {
		PostoSala postoSala; 
		for (int i = 0; i < numPostiTotali; i++) {
			// si sceglie di riservare i primi posti per i disabili
			if (i<numPostazioniDisabili)
				postoSala = new PostazioneDisabile(i+1);
			else
				postoSala = new Poltrona(i+1);
			postiSala.put(postoSala.getNumero(), postoSala);			
		}
	}

	public String getNome() {
		return nome;
	}

	public int getNumPostiTotali() {
		return numPostiTotali;
	}
	
	public int getNumPoltrone() {
		return numPoltrone;
	}
	
	public int getNumPostazioniDisabili() {
		return numPostazioniDisabili;
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
		result.append(", numero posti totali: " + this.numPostiTotali);
		result.append(", numero poltrone: " + this.numPoltrone);
		result.append(", numero postazioni disabili: " + this.numPostazioniDisabili);
		result.append(", 2D: " + this._2D);
		result.append(", 3D: " + this._3D);
		return result.toString();
	}
		
}
