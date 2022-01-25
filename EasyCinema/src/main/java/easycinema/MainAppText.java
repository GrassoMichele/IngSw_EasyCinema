package easycinema;

import easycinema.dominio.EasyCinema;
import easycinema.interfaccia.text.Console;
import easycinema.interfaccia.text.ElencoComandi;


public class MainAppText {
	public static void main(String[] args) {
		EasyCinema easyCinema = new EasyCinema();
		Console homeConsole = new Console(ElencoComandi.HOME, "HOME");
		homeConsole.start(easyCinema);
	}
}
