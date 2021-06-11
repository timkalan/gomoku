package vodja;

import java.util.Map;

import javax.swing.SwingWorker;

import graficni.Okno;

import java.util.concurrent.TimeUnit;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;
//import splosno.KdoIgra;

import inteligenca.Inteligenca;

/*
 * Vodja združi ostale pakete in "vodi" igro.
 * Od tu tudi izbiramo globino algoritma.
 */

public class Vodja {	
	
	// Vsi elementi igre.
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Okno okno;
	public static Igra igra = null;
	public static boolean clovekNaVrsti = false;
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca(2);
		
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	
	// Metoda, ki skrbi da igra teče.
	public static void igramo () {
		okno.osveziGUI();
		switch (igra.stanje()) {
		case ZMAGA_O: 
		case ZMAGA_X: 
		case NEODLOCENO: 
			return; // odhajamo iz metode igramo
			
		case V_TEKU: 
			Igralec igralec = igra.naPotezi();
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				clovekNaVrsti = false;
				igrajRacunalnikovoPotezo ();
				break;
			}
		}
	}
	
	// Uporaba niti za določanje računalnikove poteze v ozadju.
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				// Tu računalnik oceni svojo najboljšo potezo.
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				return poteza;
			}
			@Override
			protected void done () {
				// Ko se ocenjevanje konča jo odigra.
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					igra.odigraj(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}
		
	// Metoda za izvedbo človekove poteze.
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo ();
	}


}
