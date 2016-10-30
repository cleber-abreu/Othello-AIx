package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.tcc.othello.global.Game;
import com.tcc.othello.model.FieldStatus;

@SuppressWarnings("serial")
public class Disc extends JPanel {

	private Color color = new Color(36, 128, 48);
	private FieldStatus status;
	private int row;
	private int col;

	public Disc(int row, int col) {
		status = FieldStatus.VOID;
		this.row = row;
		this.col = col;
	}

	public Disc(FieldStatus status) {
		this.status = status;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (status == FieldStatus.WHITE) {
			g.setColor(Color.WHITE);
		} else if (status == FieldStatus.BLACK) {
			g.setColor(Color.BLACK);
		} else if (status == FieldStatus.SCORE_WHITE) {
			g.setColor(Color.WHITE);
			setBackground(Color.DARK_GRAY);
			repaint();
		} else if (status == FieldStatus.SCORE_BLACK) {
			g.setColor(Color.BLACK);
			setBackground(Color.DARK_GRAY);
			repaint();
		}

		if (status == FieldStatus.OPTION_WHITE) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.WHITE);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
			repaint();
		} else if (status == FieldStatus.OPTION_BLACK) {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
			repaint();
		}
		else if (status != FieldStatus.VOID) {
			g.fillOval(0, 0, 40, 40);
			g.dispose();
			repaint();
		}
	}
	
	public void setStatus(FieldStatus discStatus) {
		status = discStatus;
		
		if (status == FieldStatus.OPTION_BLACK || status == FieldStatus.OPTION_WHITE) {
			addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if (status == FieldStatus.OPTION_BLACK) {
						status = FieldStatus.BLACK;
						Game.moveOptions.remove(Game.getFieldVoid(row, col));
						Game.fieldsBlack.add(new com.tcc.othello.model.Field(row, col, status));
						Game.changeDiscs(row, col, status);
						MainWindow.changeTurn(FieldStatus.WHITE);
					}
					else if (status == FieldStatus.OPTION_WHITE) {
						status = FieldStatus.WHITE;
						Game.moveOptions.remove(Game.getFieldVoid(row, col));
						Game.fieldsWhite.add(new com.tcc.othello.model.Field(row, col, status));
						Game.changeDiscs(row, col, status);
						MainWindow.changeTurn(FieldStatus.BLACK);
					}
				}
			});
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(42, 42);
	}

	@Override
	public Color getBackground() {
		return color;
	}
	
	@Override
	public void setBackground(Color color) {
		this.color = color;
	}

	@Override
	public float getAlignmentX() {
		return CENTER_ALIGNMENT;
	}

	@Override
	public float getAlignmentY() {
		return CENTER_ALIGNMENT;
	}

	public FieldStatus getStatus() {
		return status;
	}

	public Color getBgColor() {
		return color;
	}

	public void setBgColor(Color color) {
		this.color = color;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
