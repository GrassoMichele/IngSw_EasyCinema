package easycinema.dominio;

public class SalaLuxe extends Sala{

	public SalaLuxe(String nome, int numPoltrone, int numPostazioniDisabili, boolean _2d, boolean _3d) throws EccezioneDominio {
		super(nome, numPoltrone, numPostazioniDisabili, _2d, _3d);
	}

	@Override
	protected String getTipologiaSala() {
		return "Luxe";
	}
	
	@Override
	protected String getDescrizioneServizi() {
		return "climatizzazione, poltrona in pelle reclinabile e tavolino";
	}

	@Override
	protected double getMaggiorazioneTariffa() {
		return 1.0;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();	
		result.append(super.toString());
		result.append(", tipologia: Luxe");
		return result.toString();
	}
}
