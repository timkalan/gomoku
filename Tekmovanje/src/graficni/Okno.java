package graficni;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igralec;
import vodja.Vodja;
import vodja.VrstaIgralca;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener{
	
		/**
		 * JPanel, v katerega igramo
		 */
		private Platno polje;

		
		//Statusna vrstica v spodnjem delu okna
		private JLabel status;
		
		// Izbire v menujih
		private JMenuItem igraClovekRacunalnik;
		private JMenuItem igraRacunalnikClovek;
		private JMenuItem igraClovekClovek;
		private JMenuItem igraRacunalnikRacunalnik;

		/**
		 * Ustvari novo glavno okno in prični igrati igro.
		 */
		public Okno() {
			
			this.setTitle("Gomoku");
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLayout(new GridBagLayout());
		
			// menu
			JMenuBar menu_bar = new JMenuBar();
			this.setJMenuBar(menu_bar);
			JMenu igra_menu = new JMenu("Tip igre");
			menu_bar.add(igra_menu);

			igraClovekRacunalnik = new JMenuItem("človek – računalnik");
			igra_menu.add(igraClovekRacunalnik);
			igraClovekRacunalnik.addActionListener(this);
			
			igraRacunalnikClovek = new JMenuItem("računalnik – človek");
			igra_menu.add(igraRacunalnikClovek);
			igraRacunalnikClovek.addActionListener(this);
			
			igraClovekClovek = new JMenuItem("človek – človek");
			igra_menu.add(igraClovekClovek);
			igraClovekClovek.addActionListener(this);
			
			igraRacunalnikRacunalnik = new JMenuItem("računalnik – računalnik");
			igra_menu.add(igraRacunalnikRacunalnik);
			igraRacunalnikRacunalnik.addActionListener(this);

			// igralno polje
			polje = new Platno();

			GridBagConstraints polje_layout = new GridBagConstraints();
			polje_layout.gridx = 0;
			polje_layout.gridy = 0;
			polje_layout.fill = GridBagConstraints.BOTH;
			polje_layout.weightx = 1.0;
			polje_layout.weighty = 1.0;
			getContentPane().add(polje, polje_layout);
			
			// statusna vrstica za sporočila
			status = new JLabel();
			status.setFont(new Font(status.getFont().getName(),
								    status.getFont().getStyle(),
								    20));
			GridBagConstraints status_layout = new GridBagConstraints();
			status_layout.gridx = 0;
			status_layout.gridy = 1;
			status_layout.anchor = GridBagConstraints.CENTER;
			getContentPane().add(status, status_layout);
			
			status.setText("Izberite igro!");
			
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == igraClovekRacunalnik) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.C); 
				Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.R);
				Vodja.igramoNovoIgro();
			} else if (e.getSource() == igraRacunalnikClovek) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.R); 
				Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.C);
				Vodja.igramoNovoIgro();
			} else if (e.getSource() == igraClovekClovek) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.C); 
				Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.C);
				Vodja.igramoNovoIgro();
			} else if (e.getSource() == igraRacunalnikRacunalnik) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.R); 
				Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.R);
				Vodja.igramoNovoIgro();
			}
		}

		public void osveziGUI() {
			if (Vodja.igra == null) {
				status.setText("Igra ni v teku.");
			}
			else {
				switch(Vodja.igra.stanje()) {
				case NEODLOCENO: status.setText("Neodločeno!"); break;
				case V_TEKU: 
					status.setText("Na potezi je " + Vodja.igra.naPotezi() + 
							" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi())); 
					break;
				case ZMAGA_O: 
					status.setText("Zmagal je O - " + 
							Vodja.vrstaIgralca.get(Igralec.O));
					break;
				case ZMAGA_X: 
					status.setText("Zmagal je X - " + 
							Vodja.vrstaIgralca.get(Igralec.X));
					break;
				}
			}
			polje.repaint();
		}



}
