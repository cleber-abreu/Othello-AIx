package com.tcc.othello.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class JField extends JButton {

	private static final Color defaultBackground = new Color(36, 128, 48);
	private static final Color borderBackground = new Color(2, 8, 4);
	private static final Dimension dim = new Dimension(50, 50);
	private static final Dimension dimBorderH = new Dimension(50, 20);
	private static final Dimension dimBorderV = new Dimension(20, 50);
	private static final Dimension dimBorderX = new Dimension(20, 20);
	private JDisc disc;

	public JField() {
		this.setBackground(defaultBackground);
		this.setSize(dim);
		this.setPreferredSize(dim);
		disc = new JDisc();
		add(disc);
	}

	public JField(int i) {
		this.setBackground(borderBackground);
		this.add(new JLabel(String.valueOf(i)) {
			@Override
			public float getAlignmentX() {
				return CENTER_ALIGNMENT;
			}

			@Override
			public float getAlignmentY() {
				return CENTER_ALIGNMENT;
			}

			@Override
			public Dimension getSize() {
				return dimBorderV;
			}
			
			@Override
			public Color getForeground() {
				return Color.LIGHT_GRAY;
			}
		});
		this.setSize(dimBorderV);
		this.setPreferredSize(dimBorderV);
	}

	public JField(String c) {

		if (c == " ") {
			this.setSize(dimBorderX);
			this.setPreferredSize(dimBorderX);

		} else {
			this.setSize(dimBorderH);
			this.setPreferredSize(dimBorderH);
		}

		this.setBackground(borderBackground);
		this.add(new JLabel(c) {
			
			@Override
			public float getAlignmentX() {
				return CENTER_ALIGNMENT;
			}

			@Override
			public float getAlignmentY() {
				return CENTER_ALIGNMENT;
			}

			@Override
			public Dimension getSize() {
				return dimBorderH;
			}
			
			@Override
			public Color getForeground() {
				return Color.LIGHT_GRAY;
			}
		});
	}

	public JDisc getDisc() {
		return disc;
	}

	public void setDisc(JDisc disc) {
		this.disc = disc;
	}

}
