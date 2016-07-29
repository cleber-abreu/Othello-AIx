package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import controller.Game;
import model.FieldStatus;

@SuppressWarnings("serial")
public class Disc extends JPanel {

	private Color bgColor = new Color(36, 128, 48);
	private FieldStatus discStatus;
	private int row;
	private int col;

	public Disc(int row, int col) {
		discStatus = FieldStatus.VOID;
		this.row = row;
		this.col = col;
	}

	public Disc(FieldStatus status) {
		discStatus = status;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (discStatus == FieldStatus.WHITE) {
			g.setColor(Color.WHITE);
		} else if (discStatus == FieldStatus.BLACK) {
			g.setColor(Color.BLACK);
		} else if (discStatus == FieldStatus.SCORE_WHITE) {
			g.setColor(Color.WHITE);
			setBackground(Color.DARK_GRAY);
			repaint();
		} else if (discStatus == FieldStatus.SCORE_BLACK) {
			g.setColor(Color.BLACK);
			setBackground(Color.DARK_GRAY);
			repaint();
		}

		if (discStatus == FieldStatus.OPTION_WHITE) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.WHITE);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
			repaint();
		} else if (discStatus == FieldStatus.OPTION_BLACK) {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
			repaint();
		}
		else if (discStatus != FieldStatus.VOID) {
			g.fillOval(0, 0, 42, 42);
			g.dispose();
			repaint();
		}
	}
	
	public void setDiscStatus(FieldStatus status) {
		discStatus = status;
		
		if (discStatus == FieldStatus.OPTION_BLACK || discStatus == FieldStatus.OPTION_WHITE) {
			addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if (discStatus == FieldStatus.OPTION_BLACK) {
						discStatus = FieldStatus.BLACK;
						Game.moveOptions.remove(Game.getFieldVoid(row, col));
						Game.fieldsBlack.add(new model.Field(row, col, discStatus));
						Game.changeDiscs(row, col, discStatus);
						MainWindow.changeTurn(FieldStatus.WHITE);
						repaint();
					}
					else if (discStatus == FieldStatus.OPTION_WHITE) {
						discStatus = FieldStatus.WHITE;
						Game.moveOptions.remove(Game.getFieldVoid(row, col));
						Game.fieldsWhite.add(new model.Field(row, col, discStatus));
						Game.changeDiscs(row, col, discStatus);
						MainWindow.changeTurn(FieldStatus.BLACK);
						repaint();
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
		return bgColor;
	}
	
	@Override
	public void setBackground(Color bg) {
		bgColor = bg;
	}

	@Override
	public float getAlignmentX() {
		return CENTER_ALIGNMENT;
	}

	@Override
	public float getAlignmentY() {
		return CENTER_ALIGNMENT;
	}

	public FieldStatus getDiscStatus() {
		return discStatus;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
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
