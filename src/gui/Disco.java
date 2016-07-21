package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Disco extends JPanel {
	
	private boolean ocupado = false;

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
				setOcupado(true);
			}
		});
	}

	public boolean ocupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (ocupado) {
			g.setColor(Color.WHITE);
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
}
