package logika;

/*
 * Možni igralci.
 */

public enum Igralec {
	X, O;

	public Igralec nasprotnik() {
		// Spremenimo trenutnega igralca
		return (this == X ? O : X);
	}

	public Polje getPolje() {
		// Ugotovimo, čigavo polje je
		return (this == X ? Polje.X : Polje.O);
	}
}
