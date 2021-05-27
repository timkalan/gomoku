package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OceniPozicijo {
	
	// Hevristična funkcija za oceno pozicije.
	
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		return ocena;	
	}
	
	public static int oceniVrsto (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_X = 0;
		int count_O = 0;
		for (int k = 0; k < Igra.M && (count_X == 0 || count_O == 0); k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case O: count_O += 1; break;
			case X: count_X += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_O > 0 && count_X > 0) { return 0; }
		else if (jaz == Igralec.O) { return count_O - count_X; }
		else { return count_X - count_O; }
	}
	

}


