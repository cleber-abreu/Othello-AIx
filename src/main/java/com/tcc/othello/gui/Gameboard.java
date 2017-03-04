package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.tcc.othello.model.BoardObservable;
import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Locale;

@SuppressWarnings("serial")
public class Gameboard extends JPanel {

	private JField[] fields;
	BoardObservable boardObservable;
	
	public JField getField(Locale locale) {
		return fields[locale.getId()];
	}

	public Gameboard(BoardObservable boardObservable) {
		this.boardObservable = boardObservable;
		setLayout(new GridBagLayout());
		setBackground(Color.DARK_GRAY);
		Color colorLine = new Color(6, 97, 18);
		fields = new JField[64];
		String[] lettersBorder = { " ", "A", "B", "C", "D", "E", "F", "G", "H", " " };
		Border border = null;

		GridBagConstraints grid = new GridBagConstraints();
		int id = 0;
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				grid.gridx = col;
				grid.gridy = row;
				
				// IDENTIFICAÇÃO DE LINHAS E COLUNAS
				if (row == 0 || row == 9) {
					JField current = new JField(lettersBorder[col]);
					add(current, grid);
					current.setBorder(null);
				} else if ((row != 0 || row != 9) && (col == 0 || col == 9)) {
					JField current = new JField(row);
					add(current, grid);
					current.setBorder(null);
					
				// CAMPOS DO TABULEIRO
				} else {
					final int finalId = id;
					fields[id] = new JField();
					fields[id].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (FieldStatus.VOID == fields[finalId].getDisc().getStatus()) {
								Gameboard.this.boardObservable.onBoardClick(new Locale(finalId));
							}
						}
					});
					
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
					fields[id].setBorder(border);
					add(fields[id], grid);
					id++;
				}
			}
			repaint();
		}
	}
	
	public void paintMovement(FieldStatus colorPlayer, Locale locale) {
		fields[locale.getId()].getDisc().setStatus(colorPlayer);
		repaint();
	}
	
	public void paintMovement(FieldStatus colorPlayer, ArrayList<Locale> discs) {
		for (Locale locale : discs) {
			paintMovement(colorPlayer, locale);
		}
	}
	
	public void clearAll() {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getDisc() != null) {
				fields[i].getDisc().setStatus(FieldStatus.VOID);
			}
		}
	}

}
