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

	public Tabuleiro() {

		setLayout(new GridBagLayout());
		setBackground(Color.DARK_GRAY);
		Color colorLine = new Color(6, 97, 18);
		Campo campo;
		String[] letras = { " ", "A", "B", "C", "D", "E", "F", "G", "H", " " };
		Border border = null;

		GridBagConstraints grid = new GridBagConstraints();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				grid.gridx = col;
				grid.gridy = row;

				if (row == 0 || row == 9) {
					campo = new Campo(letras[col]);
					border = null;
				} else if ((row != 0 || row != 9) && (col == 0 || col == 9)) {
					campo = new Campo(row);
					border = null;
				} else {
					campo = new Campo();

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
				campo.setBorder(border);
				add(campo, grid);
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					repaint();
				}
			});
		}
	}

}
