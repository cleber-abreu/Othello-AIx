package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class Tabuleiro extends JPanel {

	Campo[][] campos;
	
	public Tabuleiro() {

		setLayout(new GridBagLayout());
		setBackground(Color.DARK_GRAY);
		Color colorLine = new Color(6, 97, 18);
		campos = new Campo[10][10];
		String[] letras = { " ", "A", "B", "C", "D", "E", "F", "G", "H", " " };
		Border border = null;

		GridBagConstraints grid = new GridBagConstraints();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				grid.gridx = col;
				grid.gridy = row;

				if (row == 0 || row == 9) {
					campos[row][col]= new Campo(letras[col]);
					border = null;
				} else if ((row != 0 || row != 9) && (col == 0 || col == 9)) {
					campos[row][col]= new Campo(row);
					border = null;
				} else {
					campos[row][col]= new Campo();

					if (row < 9) {
						if (col < 9) {
							border = new MatteBorder(1, 1, 0, 0, colorLine);
						} else {
							border = new MatteBorder(1, 1, 0, 1, colorLine);
						}
					} else {
						if (col < 8) {
							border = new MatteBorder(1, 1, 1, 0, colorLine);
						} else {
							border = new MatteBorder(1, 1, 1, 1, colorLine);
						}
					}
				}
				campos[row][col].setBorder(border);
				add(campos[row][col], grid);
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					repaint();
				}
			});
		}
		
		campos[4][4].getDisco().setEstatos(Estatos.BRANCO);
		campos[5][5].getDisco().setEstatos(Estatos.BRANCO);
		campos[4][5].getDisco().setEstatos(Estatos.PRETO);
		campos[5][4].getDisco().setEstatos(Estatos.PRETO);
		campos[6][6].getDisco().setEstatos(Estatos.BRANCO_OPCAO);
		campos[6][5].getDisco().setEstatos(Estatos.PRETO_OPCAO);
		
	}

}
