import java.util.Random;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


	public class Labyrinthe extends JFrame {
		JPanel p;

		static final int p_width = 400;
		static final int p_height = 200;
		static final int b_width = 90;
		static final int b_height = 50;

		Labyrinthe() {
			p = new JPanel();
			p.setPreferredSize(new Dimension(p_width,p_height));
			p.setLayout(null);
			JButton b_1 = new JButton("niveau 1");
			b_1.setBounds(p_width/4-b_width/2,(p_height-b_height)/2,b_width,b_height);
			b_1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					final int niveau = 1;
					ecran_de_jeu(niveau);
				}
			});
			p.add(b_1);
			JButton b_2 = new JButton("niveau 2");
			b_2.setBounds(2*p_width/4-b_width/2,(p_height-b_height)/2,b_width,b_height);
			b_2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					final int niveau = 2;
					ecran_de_jeu(niveau);
				}
			});
			p.add(b_2);
			JButton b_3 = new JButton("niveau 3");
			b_3.setBounds(3*p_width/4-b_width/2,(p_height-b_height)/2,b_width,b_height);
			b_3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					final int niveau = 3;
					ecran_de_jeu(niveau);
				}
			});
			p.add(b_3);
			this.setTitle("Jeu");
			this.add(p);
			this.pack();
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
		}

		public void ecran_de_jeu(int niveau) {
			this.remove(p);
			GamePanel panel = new GamePanel(niveau);
			this.add(panel);
			this.pack();
			this.setVisible(true);
			panel.requestFocusInWindow();
			this.setLocationRelativeTo(null);
		}
	}