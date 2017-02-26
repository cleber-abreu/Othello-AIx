package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.Field;
import com.tcc.othello.model.FieldStatus;

@SuppressWarnings("serial")
public class Gameboard extends JPanel {

	private JField[][] fields;
	
	public JField getField(int row, int col) {
		return fields[row][col];
	}

	public Gameboard(BoardObservable boardObservable) {
		setLayout(new GridBagLayout());
		setBackground(Color.DARK_GRAY);
		Color colorLine = new Color(6, 97, 18);
		fields = new JField[10][10];
		String[] lettersBorder = { " ", "A", "B", "C", "D", "E", "F", "G", "H", " " };
		Border border = null;

		GridBagConstraints grid = new GridBagConstraints();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				grid.gridx = col;
				grid.gridy = row;

				if (row == 0 || row == 9) {
					fields[row][col]= new JField(lettersBorder[col]);
					border = null;
				} else if ((row != 0 || row != 9) && (col == 0 || col == 9)) {
					fields[row][col]= new JField(row);
					border = null;
				} else {
					fields[row][col]= new JField(row, col);

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
	
	public void drawDiscs(ArrayList<Field> fields) {
		for (Field field : fields) {
			this.fields[field.getRow()][field.getCol()]
					.getDisc().setStatus(field.getStatus());
			this.fields[field.getRow()][field.getCol()]
					.getDisc().repaint();
		}
	}
	
	public void drawMoveOptions(ArrayList<Field> fields) {
		for (Field field : fields) {
			this.fields[field.getRow()][field.getCol()]
					.getDisc().setStatus(field.getStatus());
			this.fields[field.getRow()][field.getCol()]
					.getDisc().repaint();
		}
	}
	
	public void clearDiscs(ArrayList<Field> fieldsBlack, ArrayList<Field> fieldsWhite) {
		if (fieldsBlack != null) {
			for (Field field : fieldsBlack) {
				this.fields[field.getRow()][field.getCol()]
						.getDisc().setStatus(FieldStatus.VOID);
				this.fields[field.getRow()][field.getCol()]
						.getDisc().repaint();
			}
		}
		
		if (fieldsWhite != null) {
			for (Field field : fieldsWhite) {
				this.fields[field.getRow()][field.getCol()]
						.getDisc().setStatus(FieldStatus.VOID);
				this.fields[field.getRow()][field.getCol()]
						.getDisc().repaint();
			}
		}
	}
	
	public void clearAll() {
		for (int row = 0; row < fields.length; row++) {
			for (int col = 0; col < fields.length; col++) {
				if (fields[row][col].getDisc() != null)
					fields[row][col].getDisc().setStatus(FieldStatus.VOID);
			}
		}
	}
	
	public void clearMoveOptions(ArrayList<Field> fields) {
			for (Field field : fields) {
				if (fields != null) {
					this.fields[field.getRow()][field.getCol()]
							.getDisc().setStatus(FieldStatus.VOID);
					this.fields[field.getRow()][field.getCol()]
							.getDisc().repaint();
			}
		}
	}
	
}
