package vodja;

/*
 * Razred za reprezentacijo naših dveh tipov igralca - 
 * človeka in računalnika.
 */

public enum VrstaIgralca {
	R, C; 

	@Override
	public String toString() {
		switch (this) {
		case C: return "človek";
		case R: return "računalnik";
		default: assert false; return "";
		}
	}

}
