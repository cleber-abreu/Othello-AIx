package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import model.FieldStatus;

@SuppressWarnings("serial")
public class Disc extends JPanel {

	private FieldStatus estatos;
	private Color bgColor = new Color(36, 128, 48);

	public Disc() {
		estatos = FieldStatus.VOID;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(new MatteBorder(1, 1, 1, 1, Color.GREEN));
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(null);
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (estatos == FieldStatus.VOID)
					estatos = FieldStatus.WHITE;
				repaint();
			}
		});
	}

	public Disc(FieldStatus est) {
		estatos = est;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (estatos == FieldStatus.WHITE) {
			g.setColor(Color.WHITE);
		} else if (estatos == FieldStatus.BLACK) {
			g.setColor(Color.BLACK);
		} else if (estatos == FieldStatus.SCORE_WHITE) {
			g.setColor(Color.WHITE);
			setBackground(Color.DARK_GRAY);
			repaint();
		} else if (estatos == FieldStatus.SCORE_BLACK) {
			g.setColor(Color.BLACK);
			setBackground(Color.DARK_GRAY);
			repaint();
		}

		if (estatos == FieldStatus.OPTION_WHITE) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.WHITE);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
		} else if (estatos == FieldStatus.OPTION_BLACK) {
			g.setColor(Color.DARK_GRAY);
			g.fillOval(15, 15, 10, 10);
			g.setColor(Color.BLACK);
			g.drawOval(17, 17, 6, 6);
			g.dispose();
		}
		else if (estatos != FieldStatus.VOID) {
			g.fillOval(0, 0, 42, 42);
			g.dispose();
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

	public FieldStatus getEstatos() {
		return estatos;
	}

	public void setEstatos(FieldStatus estatos) {
		this.estatos = estatos;
	}

}
