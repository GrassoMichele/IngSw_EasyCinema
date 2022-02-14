package easycinema.interfaccia.text;

import easycinema.IEasyCinema;

public class Console {
	private int tipologiaConsole;
	private String titolo;
	
	public Console(int tipologiaConsole, String titolo) {
		 this.tipologiaConsole = tipologiaConsole;
		 this.titolo = titolo;
	}
	
	public void start(IEasyCinema easyCinema) {
		Comando comando;
		do {
			stampaMenu();
			comando = Parser.getInstance().getComando(tipologiaConsole);
			comando.esegui(easyCinema);
			System.out.println();
		} while(!comando.getCodiceComando().equals("0") && !comando.exit);
			
		if (tipologiaConsole == ElencoComandi.LOGIN)  
			System.out.println("Arrivederci...");
	}

    private void stampaMenu() {
    	String separatore = new String(new char[30]).replace("\0", "*");
    	System.out.println(separatore);
        System.out.println(titolo);
        System.out.println(separatore);
		System.out.println(ElencoComandi.elencoTuttiComandi(tipologiaConsole));
		System.out.println("Seleziona:");
	}
}
