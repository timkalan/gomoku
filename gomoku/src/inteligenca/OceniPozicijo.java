package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

// Hevristična funkcija za oceno pozicije.

public class OceniPozicijo {
	
	// Združimo ocene vseh vrst.
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		return ocena;	
	}
	
	// Ocenimo posamezno vrsto z vidika danega igralca.
	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_X = 0;
		int count_O = 0;
		
		// Pogledamo, ali v vrsti prej najdemo bel ali črn kamenček 
		// oz. križec ali krožec.
		for (int k = 0; k < Igra.M && (count_X == 0 || count_O == 0); k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case O: count_O += 1; break;
			case X: count_X += 1; break;
			case PRAZNO: break;
			}
		}
		// Ocena je odvisna od trenutnega igralca.
		if (count_O > 0 && count_X > 0) { return 0; }
		else if (jaz == Igralec.O) { return count_O - count_X; }
		else { return count_X - count_O; }
	}
}


