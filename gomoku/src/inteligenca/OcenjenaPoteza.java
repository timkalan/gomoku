package inteligenca;

import splosno.Koordinati;

/*
 * Razred predstavlja dvojico poteze in njene ocene.
 */

public class OcenjenaPoteza {
	
	Koordinati poteza;
	int ocena;
	
	public OcenjenaPoteza (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	// Pomembno je, da poteze lahko med seboj primerjamo.
	public int compareTo (OcenjenaPoteza op) {
		if (this.ocena < op.ocena) return -1;
		else if (this.ocena > op.ocena) return 1;
		else return 0;
	}

}
