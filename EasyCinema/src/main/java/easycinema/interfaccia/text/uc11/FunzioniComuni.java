package easycinema.interfaccia.text.uc11;

import java.util.List;

public class FunzioniComuni {
	
	public static void stampaPosti(List<Integer> posti) {
		for (int i=0 ; i<posti.size(); i++) {
			int postoD = posti.get(i);
			String posto;
			if (postoD <= 9)
				posto = "0"+postoD;
			else
				posto = String.valueOf(postoD);
			System.out.print(" " + posto);
			if (i%30 == 29)
				System.out.println();
		}	
		System.out.println("\n");
	}
}
