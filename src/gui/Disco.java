package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Disco extends JPanel {

	// private boolean ocupado = false;
	private int color = 0;

	public Disco() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Color.GREEN);
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(null);
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setColor(1);;
			}
		});
	}

	public Disco(int c) {
		this.setColor(c);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (getColor() != 0) {
			if (getColor() == 1) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.BLACK);
			}
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
		return new Color(36, 128, 48);
	}

	@Override
	public float getAlignmentX() {
		return CENTER_ALIGNMENT;
	}

	@Override
	public float getAlignmentY() {
		return CENTER_ALIGNMENT;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int i) {
		this.color = i;
		repaint();
	}
}
