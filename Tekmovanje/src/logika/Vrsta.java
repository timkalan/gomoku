package logika;

import java.util.Arrays;

/**
 * Objekt, ki predstavlja M sosednjih polj, njihov pregled je zadosten za
 * določitev zmagovalca.
 */
public class Vrsta {
	// Vrsta je predstavljena z dvema tabelama dolžine M.
	// To sta tabeli x in y koordinat.
	public int[] x;
	public int[] y;
	
	public Vrsta(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		// lepši zipis vrste
		return "Vrsta [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
}
