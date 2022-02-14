package easycinema.dominio;

import easycinema.EccezioneDominio;

public class SalaBasic extends Sala {

	public SalaBasic(String nome, int numPoltrone, int numPostazioniDisabili, boolean _2d, boolean _3d) throws EccezioneDominio {
		super(nome, numPoltrone, numPostazioniDisabili, _2d, _3d);
	}
	
	@Override
	protected String getTipologiaSala() {
		return "Basic";
	}
	
	@Override
	protected String getDescrizioneServizi() {
		return "climatizzazione";
	}

	@Override
	protected Double getMaggiorazioneTariffa() {
		return 0.0;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();		
		result.append(super.toString());
		result.append(", tipologia: Basic");
		return result.toString();
	}
}
