package easycinema.interfaccia.text;

import easycinema.dominio.EasyCinema;

public class Console {
	private int tipologiaConsole;
	private String titolo;
	
	public Console(int tipologiaConsole, String titolo) {
		 this.tipologiaConsole = tipologiaConsole;
		 this.titolo = titolo;
	}
	
	public void start(EasyCinema easyCinema) {
		Comando comando;
		do {
			stampaMenu();
			comando = Parser.getInstance().getComando(tipologiaConsole);
			comando.esegui(easyCinema);
			System.out.println();
		} while(!comando.getCodiceComando().equals("0") && !comando.exit);
			
		if (tipologiaConsole == ElencoComandi.HOME)  
			System.out.println("   Arrivederci...");
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
