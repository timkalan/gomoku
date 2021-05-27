
import graficni.Okno;
import vodja.Vodja;

// razred, prek katerega poženemo aplikacijo
public class Gomoku {
	
	public static void main(String[] args) {
		Okno glavno_okno = new Okno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
	}

}