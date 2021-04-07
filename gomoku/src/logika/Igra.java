package logika;

import java.util.LinkedList;
import java.util.List;

public class Igra {

	
	// Velikost igralne pološče je N x N.
	public static final int N = 15;
	public static final int M = 5;
	
	// Pomožen seznam vseh vrstah na plošči.
	private static final List<Vrsta> VRSTE = new LinkedList<Vrsta>();

	static {
		// Ta koda se izvede na začetku, ko se prvič požene program.
		// Njena naloga je, da inicializira vrednosti statičnih
		// spremenljivk.
		
		// Iniciraliziramo N-vrste
		int[][] smer = {{1,0}, {0,1}, {1,1}, {1,-1}};
		for (int i = 0; i<N-M;i++) {
			for (int j = 0; j<N-M ;j++) {
				for (int x = 0; x < M; x++) {
					for (int y = 0; y < M; y++) {
						for (int[] s : smer) {
							int dx = s[0];
							int dy = s[1];
							// če je skrajno polje terice še na plošči, jo dodamo med terice
							if ((0 <= x + (M-1) * dx) && (x + (M-1) * dx < M) && 
								(0 <= y + (M-1) * dy) && (y + (M-1) * dy < M)) {
								int[] vrsta_x = new int[M];
								int[] vrsta_y = new int[M];
								for (int k = 0; k < M; k++) {
									vrsta_x[k] = x + i + dx * k;
									vrsta_y[k] = y + j + dy * k;
								}
								VRSTE.add(new Vrsta(vrsta_x, vrsta_y));
							}
						}
					}
				}
			}
		}
	}
	// Igralno polje
	private Polje[][] plosca;
	
		
	// Igralec, ki je trenutno na potezi.
	// Vrednost je poljubna, če je igre konec (se pravi, lahko je napačna).
	public Igralec naPotezi;


	/**
	 * Nova igra, v začetni poziciji je prazna in na potezi je O.
	 */
	public Igra() {
		plosca = new Polje[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		naPotezi = Igralec.O;
	}
	

	/**
	 * @return seznam možnih potez
	 */
	public List<Koordinati> poteze() {
		LinkedList<Koordinati> ps = new LinkedList<Koordinati>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) {
					ps.add(new Koordinati(i, j));
				}
			}
		}
		return ps;
	}

	/**
	 * @param t
	 * @return igralec, ki ima zapolnjeno vrsto @{t}, ali {@null}, če nihče
	 */
	private Igralec cigavaVrsta(Vrsta t) {
		int count_X = 0;
		int count_O = 0;

				for (int k = 0; k < M && (count_X == 0 || count_O == 0); k++) {
					switch (plosca[t.x[k]][t.y[k]]) {
					case O: count_O += 1; break;
					case X: count_X += 1; break;
					case PRAZNO: break;
					}
					if (count_O == M) { return Igralec.O; }
					else if (count_X == M) { return Igralec.X; }
				}
			
		
		return null;
	}

	/**
	 * @return zmagovalna vrsta, ali {@null}, če je ni
	 */
	public Vrsta zmagovalnaVrsta() {
		for (Vrsta t : VRSTE) {
			Igralec lastnik = cigavaVrsta(t);
			if (lastnik != null) return t;
		}
		return null;
	}
	
	/**
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		Vrsta t = zmagovalnaVrsta();
		if (t != null) {
			switch (plosca[t.x[0]][t.y[0]]) {
			case O: return Stanje.ZMAGA_O; 
			case X: return Stanje.ZMAGA_X;
			case PRAZNO: assert false;
			}
		}
		// Ali imamo kakšno prazno polje?
		// Če ga imamo, igre ni konec in je nekdo na potezi
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (plosca[i][j] == Polje.PRAZNO) return Stanje.V_TEKU;
			}
		}
		// Polje je polno, rezultat je neodločen
		return Stanje.NEODLOCENO;
	}

	/**
	 * Odigraj potezo p.
	 * 
	 * @param p
	 * @return true, če je bila poteza uspešno odigrana
	 */
	public boolean odigraj(Koordinati p) {
		if (plosca[p.getX()][p.getY()] == Polje.PRAZNO) {
			plosca[p.getX()][p.getY()] = naPotezi.getPolje();
			naPotezi = naPotezi.nasprotnik();
			return true;
		}
		else {
			return false;
		}
	}
	
	public String natisni() {
		String natis = "\n";
		for (int i = 0; i < N; i++) {
			for (int j = 0;j < N; j++) {
				if (plosca[i][j] == Polje.O) natis += "| O ";
				else if (plosca[i][j] == Polje.X) natis += "| X ";
				else natis += "| _ ";
			}
			natis += "| \n";
		}
	return natis;
	}
	
	

}
