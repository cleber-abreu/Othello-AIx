package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.tcc.othello.model.FieldStatus;
import com.tcc.othello.model.Player;

@SuppressWarnings("serial")
public class JDisc extends JPanel {

	private Color color = new Color(36, 128, 48);
	private FieldStatus status;
	private int row;
	private int col;

	public JDisc(int row, int col) {
		status = FieldStatus.VOID;
		this.row = row;
		this.col = col;
	}

	public JDisc(FieldStatus status) {
		this.status = status;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (status == FieldStatus.WHITE) {
			g.setColor(Color.WHITE);
		} else if (status == FieldStatus.BLACK) {
			g.setColor(Color.BLACK);
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
	}
	
	public void enableOnClick(final Player player) {
		
		if (status == FieldStatus.OPTION_BLACK || status == FieldStatus.OPTION_WHITE) {
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					player.addDisc(row, col);
					MainWindow.getGame().changeTurn();
					System.out.println("RETORNA AO LOOP "+row+","+col);
					MainWindow.getGame().run();
					System.out.println(row+","+col);
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
