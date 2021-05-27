package inteligenca;

import java.util.List;
//import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;
import splosno.KdoIgra;

// Implementiran naključni minimax
// TODO: časovna omejitev - 5 sek
public class Inteligenca extends KdoIgra {
	
// private static final Random RANDOM = new Random();
	
//	private static final int ZMAGA = 100; // vrednost zmage, več kot vsaka druga ocena pozicije
//	private static final int NEODLOC = 0;  // vrednost neodločene igre	
//	
//	private int globina;
//	
//	public Inteligenca (int globina) {
//		super("naključni minimax (" + globina + ")");
//		this.globina = globina;		
//	}
//	
//	public Koordinati izberiPotezo (Igra igra) {
//		List<OcenjenaPoteza> ocenjenePoteze = najboljsePoteze(igra, this.globina);
//		System.out.println(ocenjenePoteze.size() + " potez z vrednostjo " + ocenjenePoteze.get(0).ocena);
//		int i = RANDOM.nextInt(ocenjenePoteze.size());	
//		return ocenjenePoteze.get(i).poteza;		
//	}
//	
//	// vrne seznam vseh potez, ki imajo največjo vrednost z vidike trenutnega igralca na potezi
//	public static List<OcenjenaPoteza> najboljsePoteze(Igra igra, int globina) {
//		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
//		List<Koordinati> moznePoteze = igra.poteze();
//		for (Koordinati p: moznePoteze) {
//			Igra kopijaIgre = new Igra(igra); 
//			kopijaIgre.odigraj (p);	//poskusimo vsako potezo v novi kopiji igre
//			int ocena;
//			switch (kopijaIgre.stanje()) {
//			case ZMAGA_O:
//			case ZMAGA_X: ocena = ZMAGA; break; // p je zmagovalna poteza
//			case NEODLOCENO: ocena = NEODLOC; break;
//			default: //nekdo je na potezi
//				if (globina==1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre,igra.naPotezi());
//				else ocena = //negacija ocene z vidike dgrugega igralca
//						-najboljsePoteze(kopijaIgre,globina-1).get(0).ocena;  
//			}
//			najboljsePoteze.addIfBest(new OcenjenaPoteza(p, ocena));			
//		}
//		return najboljsePoteze.list();
//	}

	private static final int ZMAGA = 100; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	private int globina;
	
	public Inteligenca (int globina) {
		super("alphabeta globina " + globina);
		this.globina = globina;
	}
	
	public Koordinati izberiPotezo (Igra igra) {
		// Na začetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza;
	}
	
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.naPotezi() == jaz) {ocena = ZGUBA;} else {ocena = ZMAGA;}
		List<Koordinati> moznePoteze = igra.poteze();
		Koordinati kandidat = moznePoteze.get(0); // Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocenap;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_O: ocenap = (jaz == Igralec.O ? ZMAGA : ZGUBA); break;
			case ZMAGA_X: ocenap = (jaz == Igralec.X ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocenap = NEODLOC; break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			if (igra.naPotezi() == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			} else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alpha >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza (kandidat, ocena);
		}
		return new OcenjenaPoteza (kandidat, ocena);
	}

}
