package easycinema;

import easycinema.fabrication.IEasyCinema;
import easycinema.fabrication.ProxyEasyCinema;
import easycinema.interfaccia.text.Console;
import easycinema.interfaccia.text.ElencoComandi;


public class MainAppText {
	public static void main(String[] args) {
		IEasyCinema easyCinema = ProxyEasyCinema.getIstanza();
		Console loginConsole = new Console(ElencoComandi.LOGIN, "EASY CINEMA");
		loginConsole.start(easyCinema);
	}
}
