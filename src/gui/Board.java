package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class Board extends JPanel {

	Field[][] fields;
	
	public Board() {

		setLayout(new GridBagLayout());
		setBackground(Color.DARK_GRAY);
		Color colorLine = new Color(6, 97, 18);
		fields = new Field[10][10];
		String[] lettersBorder = { " ", "A", "B", "C", "D", "E", "F", "G", "H", " " };
		Border border = null;

		GridBagConstraints grid = new GridBagConstraints();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				grid.gridx = col;
				grid.gridy = row;

				if (row == 0 || row == 9) {
					fields[row][col]= new Field(lettersBorder[col]);
					border = null;
				} else if ((row != 0 || row != 9) && (col == 0 || col == 9)) {
					fields[row][col]= new Field(row);
					border = null;
				} else {
					fields[row][col]= new Field();

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
				fields[row][col].setBorder(border);
				add(fields[row][col], grid);
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					repaint();
				}
			});
		}
	}
	
	public void drawDiscs(ArrayList<model.Field> fields) {
		for (model.Field field : fields) {
			this.fields[field.getRow()][field.getCol()].getDisco().setEstatos(field.getStatus());
		}
	}

}
